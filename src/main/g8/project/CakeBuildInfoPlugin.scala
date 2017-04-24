// Copyright 2015 - 2016 Sam Halliday (derived from sbt-sensible)
// License: http://www.apache.org/licenses/LICENSE-2.0
package cake

import scala.util._

import sbt._
import sbt.IO._
import sbt.Keys._

import sbtbuildinfo.BuildInfoPlugin, BuildInfoPlugin.autoImport._

// kicks in if the Project uses the BuildInfoPlugin
object CakeBuildInfoPlugin extends AutoPlugin {
  override def requires = BuildInfoPlugin
  override def trigger  = allRequirements

  override val projectSettings = Seq(
    buildInfoPackage := s"\${organization.value}.\${name.value}.build",
    buildInfoKeys += BuildInfoKey.action("gitSha")(Try("git rev-parse --verify HEAD".!! dropRight 1) getOrElse "n/a"),
    buildInfoKeys += BuildInfoKey.action("builtAtString")(currentDateString()),
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoOptions += BuildInfoOption.ToJson
  )

  private def currentDateString() = {
    val dtf = new java.text.SimpleDateFormat("yyyy-MM-dd")
    dtf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"))
    dtf.format(new java.util.Date())
  }
}
