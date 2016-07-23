
organization in ThisBuild := "xyz.mattclifton"

name := "play-swagger"

resolvers +=  Resolver.bintrayRepo("scalaz", "releases")

scalaVersion in ThisBuild := "2.11.7"

libraryDependencies ++=
  Dependencies.playTest ++
  Dependencies.playRoutesCompiler ++
  Dependencies.playJson ++
  Dependencies.test ++
  Dependencies.yaml

//Publish.settings

lazy val playSwagger = project in file(".")

Testing.settings

Format.settings

licenses := Seq("Apache" -> url("https://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/lynx44/play-swagger"))

pomExtra := (<scm>
  <url>git@github.com:lynx44/play-swagger.git</url>
  <connection>scm:git:git@github.com:lynx44/play-swagger.git</connection>
</scm>
  <developers>
    <developer>
      <id>lynx44</id>
      <name>Matt Clifton</name>
      <url>https://github.com/lynx44</url>
    </developer>
  </developers>)

credentials ~= { c =>
  (Option(System.getenv().get("SONATYPE_USERNAME")), Option(System.getenv().get("SONATYPE_PASSWORD"))) match {
    case (Some(username), Some(password)) =>
      c :+ Credentials(
        "Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        username,
        password)
    case _ => c
  }
}

publishTo <<= version { v => //add credentials to ~/.sbt/sonatype.sbt
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }