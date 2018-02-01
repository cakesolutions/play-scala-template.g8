import ProjectPluginKeys._

import $organisation_domain$.$organisation;format="norm,word"$.Dependencies._

// FIXME: the following Settings need to be defined on a per project basis
snapshotRepositoryResolver := None
repositoryResolver := None
issueManagementUrl := None
issueManagementProject := None

lazy val $name;format="norm,word"$ = (project in file("."))
  .aggregate(play, perf)
  .disablePlugins(HeaderPlugin)

lazy val play = (project in file("play"))
  .enablePlugins(ProjectPlugin, ProjectDockerBuildPlugin)
  .enablePlay
  .enableIntegrationTests
  .settings(
    name := "$name;format="norm"$",
    buildInfoPackage := s"\${organization.value}.$name;format="norm,word"$.build",
    libraryDependencies ++= Seq(
      Cats.core,
      guice,
      playScalatest % "it,test",
      Refined.core,
      validatedConfig,
      webjars,
      // Play WebServer client library
      ws % "it,test"
    )
  )
  .dependsOn(testCommon % "it,test")

lazy val testCommon = (project in file("testCommon"))
  .settings(
    libraryDependencies ++= Seq(
      typesafeConfig
    )
  )

lazy val perf = (project in file("perf"))
  .enablePlugins(ProjectPlugin, ProjectDockerBuildPlugin)
  .enableIntegrationTests
  .settings(
    name := "$name;format="norm"$-perf",
    buildInfoPackage := s"\${organization.value}.$name;format="norm,word"$.perf.build",
    libraryDependencies ++= Seq(
      Cats.core,
      GatlingDependencies.app,
      GatlingDependencies.http,
      GatlingDependencies.testkit,
      Refined.core,
      typesafeConfig,
      validatedConfig
    ),
    dockerExposedVolumes += "/opt/docker/results",
    dockerComposeImageTask := (
      dockerComposeImageTask dependsOn (dockerComposeImageTask in play)
    ).value,
    dockerComposeFiles += file("docker/docker-compose-perf.yml"),
    dockerComposeUpLaunchStyle := "--abort-on-container-exit"
  )
  .dependsOn(play, testCommon)

// run the WebApp as default
addCommandAlias("run", "play/run")
