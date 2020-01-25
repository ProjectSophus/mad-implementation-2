lazy val commonSettings = Seq(
    scalaVersion := "2.12.8",

    parallelExecution in Test := false,

    // Show warnings
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Ywarn-unused", "-Ywarn-dead-code", "-Ywarn-inaccessible", "-Ywarn-value-discard", "-Xlint"),

    // Dependencies
    // Scalatest
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test",
    
    // Scala parser combinators
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"
    
)

lazy val core = (project in file("core"))
    .settings(
        commonSettings,
        name := "MAD-core"
    )

    
lazy val web = (project in file("web"))
    .settings(
        commonSettings,
        libraryDependencies += guice,
        name := "MAD-web"
    ).dependsOn(core, lang).enablePlugins(PlayScala)

lazy val lang = (project in file("lang"))
    .settings(
        commonSettings,
        name := "MAD-lang"
    ).dependsOn(core)
