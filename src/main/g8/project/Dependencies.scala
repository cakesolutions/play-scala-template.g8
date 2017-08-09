package $organisation_domain$.$organisation$

import sbt._

/**
  * Dependencies referenced in project auto-plugin and build code
  */
object Dependencies {
  val cats: ModuleID = "org.typelevel" %% "cats" % "0.9.0"
  val playScalatest: ModuleID =
    "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0"
  val typesafeConfig: ModuleID = "com.typesafe" % "config" % "1.3.1"
  val validatedConfig: ModuleID =
    "net.cakesolutions" %% "validated-config" % "1.0.2"
  val webjars: ModuleID = "org.webjars" % "swagger-ui" % "3.0.21"

  object GatlingDependencies {
    val version = "2.2.5"

    val highcharts: ModuleID =
      "io.gatling.highcharts" % "gatling-charts-highcharts" % version
    val testkit: ModuleID = "io.gatling" % "gatling-test-framework" % version
  }

  object Refined {
    val version: String = "0.8.2"

    val core: ModuleID = "eu.timepit" %% "refined" % version
    val scalacheck: ModuleID = "eu.timepit" %% "refined-scalacheck" % version
  }
}
