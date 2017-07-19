import $organisation_domain$.$organisation$.Dependencies._, ProjectPluginKeys._

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
      guice,
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
      cats,
      GatlingDependencies.highcharts % "test",
      GatlingDependencies.testkit % "test",
      Refined.core,
      typesafeConfig,
      validatedConfig
    )
  )
  .dependsOn(play % "test->compile;test->it")

// run the WebApp as default
addCommandAlias("run", "play/run")
