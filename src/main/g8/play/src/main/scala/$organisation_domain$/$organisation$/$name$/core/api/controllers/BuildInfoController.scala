package $organisation_domain$.$organisation$.$name$.core.api.controllers

import javax.inject._

import play.api.mvc._

import $organisation_domain$.$organisation$.$name$.build.BuildInfo

@Singleton
class BuildInfoController @Inject()(
  override val controllerComponents: ControllerComponents
) extends BaseController {

  def info: Action[AnyContent] = Action {
    Ok(BuildInfo.toJson).as("application/json")
  }

}
