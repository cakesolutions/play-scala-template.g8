package $organisation_domain$.$organisation$.$name$

import com.typesafe.config.ConfigFactory

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class $name;format="Camel"$PerformanceTest extends Simulation {

  protected val config = ConfigFactory.load()
  protected val appUrl = {
    val scheme = config.getString("services.app.scheme")
    val host  = config.getString("services.app.host")
    val port  = config.getInt("services.app.port")
    s"\$scheme://\$host:\$port"
  }

  val httpConf = http.baseURL(s"\$appUrl/health")

  val readClients = scenario("Clients").exec(Index.refreshManyTimes)

  setUp(readClients.inject(rampUsers(100) over (10 seconds)).protocols(httpConf))
}

object Index {

  def refreshAfterOneSecond = exec(http("Index").get("/").check(status.is(200))).pause(1)

  val refreshManyTimes = repeat(10) {
    refreshAfterOneSecond
  }
}
