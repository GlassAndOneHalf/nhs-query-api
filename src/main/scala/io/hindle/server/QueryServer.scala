package io.hindle.server

import java.util.concurrent.Executors

import com.typesafe.config.ConfigFactory
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object QueryServer extends ServerApp {

  private val config = ConfigFactory.load()
  private val pool = Executors.newCachedThreadPool()

  val host = config.getString("server.hostname")
  val port = config.getInt("server.port")

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(port, host)
      .mountService(NHSSymptomApi().service)
      .withServiceExecutor(pool)
      .start
  }
}
