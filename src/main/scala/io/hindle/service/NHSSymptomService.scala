package io.hindle.service

import java.io.File

import com.typesafe.config.ConfigFactory
import io.circe.generic.auto._
import io.circe.parser._
import io.hindle.server.models.{SymptomQuery, SymptomRecord, SymptomResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.{Codec, Source}
import scalaz.concurrent.Task

class NHSSymptomService(luceneService: LuceneService) {
  private val inputDirectory = ConfigFactory.load().getString("input.directory")

  def docForQuery(query: SymptomQuery): Task[Option[SymptomResponse]] = Task {
    luceneService.executeQuery(query)
  }

  luceneService.populateIndex(Await.result(symptomFiles, 1 minute))

  private def symptomFiles: Future[Seq[SymptomRecord]] = {
    Future { new File(inputDirectory).listFiles().filter(_.isFile) }.flatMap { files =>
      Future.traverse[File, SymptomRecord, Seq](files) { file =>
        val content = Source.fromFile(file)(Codec.UTF8).mkString
        decode[SymptomRecord](content) match {
          case Left(err) => Future.failed(err)
          case Right(value) => Future.successful(value)
        }
      }
    }

    /*Future.traverse[File, SymptomRecord, Seq](new File(inputDirectory).listFiles().filter(_.isFile)) { file =>
      val content = Source.fromFile(file)(Codec.UTF8).mkString
      decode[SymptomRecord](content) match {
        case Left(err) => Future.failed(err)
        case Right(value) => Future.successful(value)
      }
    }*/
  }
}

object NHSSymptomService {
  def apply(): NHSSymptomService =
    new NHSSymptomService(LuceneService())
}
