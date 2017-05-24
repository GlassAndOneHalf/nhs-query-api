package io.hindle.server.models

import io.hindle.util.Stopwords

case class SymptomQuery(query: String) {
  /**
    * Removes all stopwords and punctuation the query and splits
    * it in to individual words
    *
    * @return Some(words) if the words array is non-empty. None otherwise.
    */
  def queryWords: Option[Seq[String]] = {
    val newQuery =
      query
        .replaceAll("[^\\w\\s]", "")
        .split("\\s+")
        .filterNot(word => Stopwords.stopwords.contains(word) || word.isEmpty)

    if (newQuery.nonEmpty) Some(newQuery) else None
  }
}
