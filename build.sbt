import AssemblyKeys._

name := "Scala Bukkit Plugin"

version := "0.0.1"

scalaVersion := "2.10.3"

resolvers ++= Seq(
        "org.bukkit" at "http://repo.bukkit.org/content/repositories/releases/",
        "trefan" at "http://maven.trefan.eu/artifactory/simple/ext-release-local/")

libraryDependencies ++= Seq(
        "org.bukkit" % "bukkit" % "1.6.4-R2.0",
        "javax.servlet" % "servlet-api" % "2.4" % "provided",
        "de.bananaco" % "bPermissions" % "2.10.7c",
        "com.palmergames.bukkit.towny" % "Towny" % "0.84.0.0")

javacOptions ++= Seq("-source", "1.5", "-target", "1.5")

scalacOptions ++= Seq("-no-specialization")

assemblySettings