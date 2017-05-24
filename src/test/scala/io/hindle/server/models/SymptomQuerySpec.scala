package io.hindle.server.models

import org.scalatest.{Matchers, WordSpec}

class SymptomQuerySpec extends WordSpec with Matchers {
  "queryWords" should {
    "return a sequence of sanitised query words if the resulting list of words is non-empty" in {
      val array = SymptomQuery("what are the symptoms of cancer?").queryWords

      array.isDefined shouldBe true
      array.get should contain theSameElementsInOrderAs Seq("symptoms", "cancer")
    }

    "return None if, after sanitising, the resulting list of words is empty" in {
      val punctQuery = SymptomQuery("!?!?? !???%£").queryWords
      val stopWordQuery = SymptomQuery("what why when who where").queryWords
      val emptyQuery = SymptomQuery("").queryWords

      punctQuery shouldBe None
      stopWordQuery shouldBe None
      emptyQuery shouldBe None
    }
  }
}
