// Please read https://github.com/cakesolutions/sbt-cake
//
// NOTE: common settings are in project/ProjectPlugin.scala but
//       anything specific to a project should go in this file. Check
//       sbt-cake for standard libraryDependencies used in various
//       Cake projects, otherwise just add dependencies explicitly
//       in this file.

// example standalone server project
val server = project
  .enablePlugins(JavaServerAppPackaging, BuildInfoPlugin, DockerPlugin)
  .enableIntegrationTests
  .settings(
    mainClass in Compile := Some(s"\${organization.value}.\${name.value}.Server"),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % versions.play // for Play's ActorFlow.actorRef function
    ) ++ deps.AkkaPersistence ++ deps.AkkaCluster ++ deps.KafkaClient ++ deps.AkkaHttp
  )

// example play app
val app = project
  .enablePlay
  .enablePlugins(BuildInfoPlugin, DockerPlugin, AshScriptPlugin)
  .settings(
    libraryDependencies ++= deps.AngularBootstrap,
    pipelineStages := Seq(digest, gzip)
  )
