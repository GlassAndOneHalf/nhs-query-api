package io.hindle.server.models

import org.apache.lucene.document.{Document, Field, StringField, TextField}

case class SymptomRecord(url: String, title: String, content: String) {

  /**
    * Creates a lucene document from the data in the symptom record.
    *
    * @return A lucene document containing the data in this record.
    */
  def toLuceneDocument: Document = {
    val doc = new Document
    doc.add(new StringField("url", url, Field.Store.YES))
    doc.add(new TextField("title", title, Field.Store.NO))
    doc.add(new TextField("content", content, Field.Store.NO))
    doc
  }
}
