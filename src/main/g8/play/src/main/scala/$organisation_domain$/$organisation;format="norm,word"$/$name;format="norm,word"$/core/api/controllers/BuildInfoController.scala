package $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.core.api.controllers

import javax.inject._

import play.api.mvc._

import $organisation_domain$.$organisation;format="norm,word"$.$name;format="norm,word"$.build.BuildInfo

@Singleton
class BuildInfoController @Inject()(
  override val controllerComponents: ControllerComponents
) extends BaseController {

  def info: Action[AnyContent] = Action {
    Ok(BuildInfo.toJson).as("application/json")
  }

}
