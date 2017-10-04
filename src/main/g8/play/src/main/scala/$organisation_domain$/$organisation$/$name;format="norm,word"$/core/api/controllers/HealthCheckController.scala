package $organisation_domain$.$organisation$.$name;format="norm,word"$.core.api.controllers

import javax.inject._

import play.api.libs.json.{Json, JsString}
import play.api.mvc._

@Singleton
class HealthCheckController @Inject()(
  override val controllerComponents: ControllerComponents
) extends BaseController {

  def health: Action[AnyContent] = Action {
    val json = Json.obj("status" -> JsString("Ok"))
    Ok(json).as("application/json")
  }

}
