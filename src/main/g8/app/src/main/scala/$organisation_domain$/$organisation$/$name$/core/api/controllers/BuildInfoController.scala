package $organisation_domain$.$organisation$.$name$.core.api.controllers

import javax.inject.Singleton

import $organisation_domain$.$organisation$.$name$.build.BuildInfo
import play.api.mvc._

@Singleton
class BuildInfoController extends Controller {

  def info = Action {
    Ok(BuildInfo.toJson)
  }

}
