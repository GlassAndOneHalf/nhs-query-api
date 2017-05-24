package io.hindle.service

import io.hindle.server.models.{SymptomQuery, SymptomRecord, SymptomResponse}
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.index.{DirectoryReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.{Directory, RAMDirectory}

class LuceneService(analyzer: Analyzer, index: Directory) {
  private val writerConfig = new IndexWriterConfig(analyzer)
  private val queryParser = new StandardQueryParser(analyzer)
  private lazy val searcher = new IndexSearcher(DirectoryReader.open(index))

  /*
   * Builds a lucene query from a list of words.
   *
   * This query will heavily favour documents where the words
   * appear in the title.
   */
  private def buildQueryFromWords(words: Seq[String]): String = {
    val titleQuery = words.map { elem =>
      s"title:$elem"
    }.mkString(" OR ")

    val contentQuery = words.map { elem =>
      s"content:$elem"
    }.mkString(" AND ")

    s"($titleQuery)^1.5 ($contentQuery)"
  }

  def executeQuery(userQuery: SymptomQuery): Option[SymptomResponse] = {
    userQuery.queryWords.flatMap { words =>
      val parsedQuery = queryParser.parse(buildQueryFromWords(words), "content")
      val hits = searcher.search(parsedQuery, 1)

      hits.scoreDocs.headOption.map { scoreDoc =>
        val resultUrl = searcher.doc(scoreDoc.doc).get("url")
        SymptomResponse(resultUrl)
      }
    }
  }

  def populateIndex(records: Seq[SymptomRecord]): Unit = {
    val writer = new IndexWriter(index, writerConfig)

    records.foreach { record =>
      writer.addDocument(record.toLuceneDocument)
    }

    writer.close()
  }

}

object LuceneService {
  def apply(): LuceneService =
    new LuceneService(new EnglishAnalyzer(), new RAMDirectory())
}
