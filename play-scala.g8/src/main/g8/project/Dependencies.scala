import sbt._

object Dependencies {

  object version {
    val logstashLogbackEncoder = "4.8"
  }

  val rootDependencies = Seq(
      "net.logstash.logback" % "logstash-logback-encoder" % version.logstashLogbackEncoder,
      play.sbt.PlayImport.ws
    )
}
