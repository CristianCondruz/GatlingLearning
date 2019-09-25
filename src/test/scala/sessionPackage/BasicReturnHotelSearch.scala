package sessionPackage
import io.gatling.core.Predef._
import io.gatling.http.Predef._

//curl -v  -X GET "http://developer.goibibo.com/api/voyager/get_hotels_by_cityid/?app_id=f939f99a&app_key=ef110837bfea9fca9f75a052702882b1&city_id=6771549831164675055"
class BasicReturnHotelSearch extends Simulation {
  val httpConf = http.baseUrl("http://developer.goibibo.com")
  val appId = "f939f99a"
  val appKey = "ef110837bfea9fca9f75a052702882b1"
  val csvFeeder_hotel = csv("data/cityId.csv").circular
  val cityId = "4761547441228189942"



  val scn = scenario("Get return Hotels").feed(csvFeeder_hotel)
    .exec(http("Get return hotel").get("/api/voyager/get_hotels_by_cityid/?app_id=" + appId + "&app_key=" + appKey + s"&city_id=${cityId}")
      .check(jsonPath("$.data.._id").find.saveAs("searchKey")))
    .exec(session =>
    {
      val myAttribute = session("searchKey")
      println("Hotel id: " + myAttribute.validate[String])
      session
    })

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}

