package $organisation_domain$.$organisation$.$name$.core.api.controllers

import javax.inject.Singleton

import play.api.libs.json.{JsString, Json}
import play.api.mvc.{Action, Controller}

@Singleton
class HealthCheckController extends Controller {

  def health = Action {
    val json = Json.obj("status" -> JsString("Ok"))
    Ok(json)
  }

}
