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

lazy val buildInfoSettings: Seq[Def.Setting[_]] = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
    name,
    version,
    scalaVersion,
    sbtVersion,
    BuildInfoKey.action("gitHash")(gitHash)
  ),
  buildInfoPackage := "$organisation_domain$.$organisation$.$name$.build",
  buildInfoOptions += BuildInfoOption.ToJson
)

lazy val dockerSettings: Seq[Def.Setting[_]] = Seq(
  packageName in Docker := "$name$",
  maintainer in Docker := "Core Devops <devops@cakesolutions.net>",
  packageSummary in Docker := "$project_description$",
  packageDescription := "$project_description$",
  dockerRepository := None,
  dockerBaseImage := "openjdk:8-alpine",
  dockerUpdateLatest := true,
  version in Docker := (version in ThisBuild).value
)

lazy val enabledPlugins = Seq(
  PlayScala,
  ScoverageSbtPlugin,
  BuildInfoPlugin,
  DockerPlugin
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation")

lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    organization := "$organisation_domain$.$organisation$.$name$",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Dependencies.rootDependencies,
    javaOptions += "-Dlogger.file=conf/logback.xml"
  )
  .enablePlugins(enabledPlugins: _*)
  .settings(buildInfoSettings: _*)
  .settings(scoverageSettings: _*)
  .settings(dockerSettings: _*)
