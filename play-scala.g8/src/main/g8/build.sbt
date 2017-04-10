import java.time.{Instant, ZoneOffset}
import java.time.format.DateTimeFormatter

import ProjectDetails._

import scala.util.Properties
import com.typesafe.sbt.packager.docker.Cmd

lazy val buildTimestamp = Instant.now().atZone(ZoneOffset.UTC)

lazy val scoverageSettings = Seq(
  coverageMinimum := 80,
  coverageFailOnMinimum := true,
  coverageExcludedPackages := """.*controllers\..*Reverse.*Controller;router.Routes.*;.*Module;.*DDBCircuitBreakerSource;.*ActivationController;com.mlbam.activation.metrics.*"""
)

/*
 * This creates an organization.BuildInfo object, which contains the specified
 * build-related metadata. Its values are accessible directly as fields, as
 * a Map, or as a Json string. The sourcecode is generated under
 * $PROJECT_PATH/target/scala-2.xx/src_managed.
 */
lazy val buildInfoSettings: Seq[Def.Setting[_]] = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
    name,
    version,
    scalaVersion,
    sbtVersion,
    BuildInfoKey.action("gitHash")(gitHash),
    BuildInfoKey.action("lastCommitHash") {
      Seq("git", "rev-parse", "HEAD").!!.trim
    }
  ),
  buildInfoPackage := "$organisation_domain$.$organisation$.$name$.build",
  buildInfoOptions += BuildInfoOption.BuildTime,
  buildInfoOptions += BuildInfoOption.ToJson
)

lazy val dockerSettings: Seq[Def.Setting[_]] = Seq(
  packageName in Docker := "$name$",
  maintainer in Docker := "Core Devops <devops@cakesolutions.net>",
  packageSummary in Docker := "$project_description$",
  packageDescription := "$project_description$",
  dockerRepository := None,
  dockerBaseImage := "8-jre-alpine",
  dockerUpdateLatest := true,
  version in Docker := (version in ThisBuild).value
)

lazy val enabledPlugins = Seq(
  PlayScala,
  ScoverageSbtPlugin,
  BuildInfoPlugin,
  DockerPlugin
)

scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding",
      "UTF-8",
      "-feature",
      "-Xlint",
      "-Xfuture",
      //"-Ywarn-unused-import", // For some reason this was causing routes to not compile, Needs investigation.
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Ywarn-unused",
      "-Xfatal-warnings"
    )

lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    organization := "$organisation_domain$.$organisation$.$name$",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Dependencies.rootDependencies,
    // Filtering out options that render the console unusable.
    scalacOptions in (Compile, console) := (scalacOptions in Compile).value
      .filterNot(
        opt =>
          Seq(
            //"-Ywarn-unused-import", // Needed to allow `import`
            "-Xfatal-warnings" // Needed to allow `???` and others
          ).contains(opt)
      ),
    javacOptions ++= Seq(
      "-source",
      "1.8"
    ),
    javacOptions in (Compile, compile) ++= Seq(
      "-target",
      "1.8",
      "-Xlint:unchecked"
    ),
    javacOptions ++= Seq(
      "-Dlogger.file=conf/logback.xml"
    ),
    shellPrompt in ThisBuild := { state =>
      val project = Project.extract(state).currentRef.project
      s"[\$project]> "
    }
  )
  .enablePlugins(enabledPlugins: _*)
  .settings(buildInfoSettings: _*)
  .settings(scoverageSettings: _*)
  .settings(dockerSettings: _*)
