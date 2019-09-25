package basicPackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._



class BasicCheckScript extends Simulation{

  val httpConf = http.baseUrl("https://api.openbrewerydb.org")

  val scn = scenario("basic get scenario").exec(http("basic get request").get("/breweries")
    .check(status.is(200)).check(regex(".*state.*")))

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
