package basicPackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class BasicFeederScript extends Simulation{

  val states = csv("data/States.csv").queue


  val httpConf = http.baseUrl("https://api.openbrewerydb.org")

  val scn = scenario("basic get scenario").feed(states).exec(http("basic get request")
    .get("/breweries?by+state=${state}")
    .check(status.is(200)).check(regex(".*state.*")))

  setUp(scn.inject(atOnceUsers(4))).protocols(httpConf)
}
