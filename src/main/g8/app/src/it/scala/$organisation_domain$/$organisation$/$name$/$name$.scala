package $organisation_domain$.$organisation$.$name$

import com.google.inject.Inject
import org.scalatest.Tag
import scala.concurrent.ExecutionContext


object Docker extends Tag("Docker")

class $name$IntegrationTest @Inject() extends RestApiIntegrationTest {
  implicit val ec: ExecutionContext = ExecutionContext.global
  "Health-check" - {
    "(when application is running)" - {
      "should always return status okay" taggedAs (Docker) in {
        wsClient
          .url("http://localhost:9000/health")
          .get()
          .map(res => {
            res.status shouldEqual 200
          })
      }
      "should return a JSON object with property status set to Ok" taggedAs (Docker) in {
        wsClient
          .url("http://localhost:9000/health")
          .get()
          .map(res => {
            (res.json \ "status").as[String] shouldEqual "Ok"
          })
      }
    }
  }
}
