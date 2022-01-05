rootProject.name = "FoldFeatures"

pluginManagement {
    plugins {
        kotlin("jvm").version("1.6.10")
        id("org.jetbrains.compose").version("1.0.1")
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
        //maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}