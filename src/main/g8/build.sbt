import $organisation_domain$.$organisation$.Dependencies._

// FIXME: the following Settings need to be defined on a per project basis
snapshotRepositoryResolver := None
repositoryResolver := None
issueManagementUrl := None
issueManagementProject := None

lazy val play = project
  .enablePlugins(ProjectDockerBuildPlugin)
  .enablePlay
  .enableIntegrationTests
  .settings(
    libraryDependencies ++= Seq(
      cats,
      playScalatest % "it,test",
      Refined.core,
      validatedConfig,
      webjars,
      // Play WebServer client library
      ws % "it,test"
    )
  )

lazy val perf = (project in file("perf"))
  .enablePlugins(ProjectPlugin, GatlingPlugin)
  .settings(
    libraryDependencies ++= Seq(
      GatlingDependencies.highcharts % "test",
      GatlingDependencies.testkit % "test"
    )
  )
  .dependsOn(play % "test->compile")

// run the WebApp as default
addCommandAlias("run", "play/run")
