package basicPackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level, LoggerContext}

class BasicGetScript extends Simulation{
  val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

  context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
  val httpConf = http.baseUrl("https://api.openbrewerydb.org")

  val scn = scenario("basic get scenario").exec(http("basic get request").get("/breweries"))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
