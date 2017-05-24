package io.hindle.server

import io.circe.generic.auto._
import io.circe.syntax._
import io.hindle.server.models.SymptomQuery
import io.hindle.service.NHSSymptomService
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl._

class NHSSymptomApi(symptomService: NHSSymptomService) {

  val service = HttpService {
    case req @ POST -> Root / "query" / "symptoms" =>
      for {
        query <- req.as(jsonOf[SymptomQuery])
        resultOpt <- symptomService.docForQuery(query)
        response <- resultOpt.map(result => Ok(result.asJson.spaces2)).getOrElse(NotFound())
      } yield response
  }
}

object NHSSymptomApi {
  def apply(): NHSSymptomApi =
    new NHSSymptomApi(NHSSymptomService())
}
