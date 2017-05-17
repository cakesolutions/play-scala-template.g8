import net.cakesolutions.CakePlatformPlugin

import sbt._
import sbt.Keys._

import scoverage.ScoverageKeys._
import wartremover._
import sbtbuildinfo.BuildInfoPlugin, BuildInfoPlugin.autoImport._

/** Example of how to apply settings across a build (difficult to do in build.sbt) */
object ProjectPlugin extends AutoPlugin {
  override def requires = CakePlatformPlugin
  override def trigger  = allRequirements

  val autoImport = ProjectPluginKeys
  import autoImport._

  // NOTE: everything in here is applied once, to the entire build
  override val buildSettings = Seq(
    name := "$name$",
    organization := "$organisation_domain$.$organisation$"
  )

  // NOTE: everything in here is applied to every project (a better `commonSettings`)
  override val projectSettings = Seq(
    // TODO (sbt-cake#8) When that is done, we can remove it from here
    scalacOptions ++= Seq("-Ypartial-unification"),
    // we have multiple microservices in this project
    buildInfoPackage := s"$organisation_domain$.$organisation$.$name$.build",
    buildInfoKeys := Seq[BuildInfoKey](
      name,
      version,
      scalaVersion,
      sbtVersion
    ),
    // NOTE: This avoids using resources to create a POM file.  We don't need it.
    publishArtifact in makePom := false
  )

}

object ProjectPluginKeys {
  // NOTE: anything in here is automatically visible in build.sbt
}
