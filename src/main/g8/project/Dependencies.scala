package $organisation_domain$.$organisation;format="norm,word"$

import sbt._

/**
  * Dependencies referenced in project auto-plugin and build code
  */
object Dependencies {
  val playScalatest: ModuleID =
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
  val typesafeConfig: ModuleID = "com.typesafe" % "config" % "1.3.2"
  val validatedConfig: ModuleID =
    "net.cakesolutions" %% "validated-config" % "1.1.2"
  val webjars: ModuleID = "org.webjars" % "swagger-ui" % "3.9.2"

  object Cats {
    private val version = "1.0.1"
    val core: ModuleID = "org.typelevel" %% "cats-core" % version
  }

  object GatlingDependencies {
    // 2.3.0 requires scala 2.12
    private val version = "2.2.5"
    val app: ModuleID = "io.gatling" % "gatling-app" % version
    val http: ModuleID = "io.gatling" % "gatling-http" % version
    val testkit: ModuleID = "io.gatling" % "gatling-test-framework" % version
  }

  object Refined {
    private val version: String = "0.8.6"
    val core: ModuleID = "eu.timepit" %% "refined" % version
    val scalacheck: ModuleID = "eu.timepit" %% "refined-scalacheck" % version
  }
}
