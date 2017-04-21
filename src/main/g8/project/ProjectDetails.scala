import sbt._

object ProjectDetails {

  lazy val gitBranchName =
    Seq("git", "symbolic-ref", "--short", "HEAD").!!.trim
  lazy val gitCommitter = Seq("git",
                              "--no-pager",
                              "show",
                              "-s",
                              "--format=\"%an <%ae>\"",
                              "HEAD").!!.trim
  lazy val gitUrl      = Seq("git", "config", "--get", "remote.origin.url").!!.trim
  lazy val gitHash     = Seq("git", "rev-parse", "--verify", "HEAD").!!.trim
  lazy val projectName = "$name$"
}
