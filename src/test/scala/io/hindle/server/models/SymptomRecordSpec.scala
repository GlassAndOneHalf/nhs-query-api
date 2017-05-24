package io.hindle.server.models

import org.scalatest.{Matchers, WordSpec}

class SymptomRecordSpec extends WordSpec with Matchers {
  "toLuceneDocument" should {
    "return a Lucene document containing the same data" in {
      val document = SymptomRecord("symptomUrl", "symptomTitle", "symptomContent").toLuceneDocument

      document.get("url") shouldBe "symptomUrl"
      document.get("title") shouldBe "symptomTitle"
      document.get("content") shouldBe "symptomContent"
    }
  }
}
