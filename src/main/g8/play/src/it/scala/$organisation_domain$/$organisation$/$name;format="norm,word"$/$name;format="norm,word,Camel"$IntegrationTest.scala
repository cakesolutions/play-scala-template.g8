package $organisation_domain$.$organisation$.$name;format="norm,word"$

import com.google.inject.Inject
import org.scalatest.Tag
import play.mvc.Http

object Docker extends Tag("Docker")

class $name;format="norm,word,Camel"$IntegrationTest @Inject() extends RestApiIntegrationTest {
  "When application is running" - {
    "Health-check" - {
      "should always return status okay" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/health")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .get()
          .map(res => {
            res.status shouldEqual 200
            (res.json \ "status").as[String] shouldEqual "Ok"
          })
      }
    }
    "Build info" - {
      "should return a JSON object with current version" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/version")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .get()
          .map(res => {
            res.status shouldEqual 200
            (res.json \ "version").as[String] should not be empty
          })
      }
    }
    "OpenAPI specs" - {
      "should return the yaml specs" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/specs.yml")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .get()
          .map(res => {
            res.status shouldEqual 200
          })
      }
      "should redirect to the API docs" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/docs")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .withFollowRedirects(false)
          .get()
          .map(res => {
            res.status shouldEqual 303
            res
              .header("Location")
              .get shouldEqual "/docs/index.html?url=/specs.yml"
          })
      }
      "should show the API docs" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/docs?url=/specs.yml")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .get()
          .map(res => {
            res.status shouldEqual 200
          })
      }
      "default route should re-direct to API docs" taggedAs (Docker) in {
        wsClient
          .url(s"\$appUrl/")
          .addHttpHeaders(Http.HeaderNames.HOST -> "localhost")
          .withFollowRedirects(false)
          .get()
          .map(res => {
            res.status shouldEqual 303
          })
      }
    }
  }
}
