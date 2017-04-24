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
    buildInfoKeys := Seq[BuildInfoKey](
      BuildInfoKey.action("name")("$name$"),
      version,
      scalaVersion,
      sbtVersion,
      BuildInfoKey.action("lastCommitSha")(Try("git rev-parse --verify HEAD".!! dropRight 1) getOrElse "n/a")
    ),
    buildInfoPackage := s"\${organization.value}.\${(name in ThisBuild).value}.build",
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoOptions += BuildInfoOption.ToJson
  )

}
