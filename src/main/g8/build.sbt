// Please read https://github.com/cakesolutions/sbt-cake
//
// NOTE: common settings are in project/ProjectPlugin.scala but
//       anything specific to a project should go in this file. Check
//       sbt-cake for standard libraryDependencies used in various
//       Cake projects, otherwise just add dependencies explicitly
//       in this file.

val play = project
  .enablePlay
  .enablePlugins(BuildInfoPlugin, DockerPlugin, AshScriptPlugin)
  .enableIntegrationTests
  .settings(
    libraryDependencies ++= Seq(
      "org.webjars"            %  "swagger-ui"         % "3.0.10",
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "it,test",
      ws % "it,test" // Play WebServer client library
    )
  )

// run the WebApp as default
addCommandAlias("run", "play/run")

// integration Tests require Docker fleet.
addCommandAlias("integrationTests", ";dockerComposeUp;it:test;dockerComposeDown")
