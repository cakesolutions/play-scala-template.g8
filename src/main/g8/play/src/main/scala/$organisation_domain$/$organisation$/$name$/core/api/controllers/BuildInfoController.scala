package $organisation_domain$.$organisation$.$name$.core.api.controllers

import javax.inject.Singleton

import play.api.mvc._

import $organisation_domain$.$organisation$.$name$.build.BuildInfo

@Singleton
class BuildInfoController extends Controller {

  def info: Action[AnyContent] = Action {
    Ok(BuildInfo.toJson).as("application/json")
  }

}
