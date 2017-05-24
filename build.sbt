name := "nhs-query-api"

version := "1.0"

scalaVersion := "2.12.2"

val circeVersion = "0.8.0"
val luceneVersion = "6.5.1"
val http4sVersion = "0.15.12a"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "org.apache.lucene" % "lucene-analyzers-common" % luceneVersion,
  "org.apache.lucene" % "lucene-queryparser" % luceneVersion,
  "org.apache.lucene" % "lucene-core" % luceneVersion,

  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,

  "com.typesafe" % "config" % "1.3.1",
  "org.clapper" %% "grizzled-slf4j" % "1.3.1",
  "org.slf4j" % "slf4j-simple" % "1.7.25",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
        