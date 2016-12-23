import sbt.Keys._

lazy val root: Project = (project in file("."))
  .settings(
    name := """I-want-my-candies""",
    version := "1.0",
    scalaVersion := "2.11.8",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0",
    libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.0",
    libraryDependencies += "me.chrons" %%% "diode" % "1.1.0",
    skip in packageJSDependencies := false
  )
  .enablePlugins(ScalaJSPlugin)

