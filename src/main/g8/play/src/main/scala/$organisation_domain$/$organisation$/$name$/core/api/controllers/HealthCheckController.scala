package $organisation_domain$.$organisation$.$name$.core.api.controllers

import javax.inject.Singleton

import play.api.libs.json.{Json, JsString}
import play.api.mvc._

@Singleton
class HealthCheckController extends Controller {

  def health: Action[AnyContent] = Action {
    val json = Json.obj("status" -> JsString("Ok"))
    Ok(json).as("application/json")
  }

}
