package sessionPackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicGetBusDetailsSessionAPI extends Simulation {

  //curl -v  -X GET "http://developer.goibibo.com/api/bus/search/?app_id=f939f99a&app_key=ef110837bfea9fca9f75a052702882b1%09&format=json&source=Bangalore&destination=Hyderabad&dateofdeparture=20180426"
  val httpConf = http.baseUrl("http://developer.goibibo.com")
  val appId = "f939f99a"
  val appKey = "ef110837bfea9fca9f75a052702882b1"
  val source = "bangalore"
  val destination = "hyderabad"
  val dateOfDeparture = "20191026"

  val csvfeeder_bus = csv("data/budDetails.csv").circular
  val scn = scenario("Get Bus Details").feed(csvfeeder_bus)
    .exec(http("Get bus request").get("/api/bus/search/?app_id=" + appId + "&app_key=" + appKey + s"%09&format=json&source=${source}"  +
    s"&destination=${destination}" + "&dateofdeparture=" + dateOfDeparture ).check(jsonPath("$.data..skey").find.saveAs("searchKey")))
      .exec(session =>
      {
        val myAttribute = session("searchKey")
        println(myAttribute.validate[String])
        session
      })

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
