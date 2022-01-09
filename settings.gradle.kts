rootProject.name = "intellij-fold-folders"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.6.10"
        id("org.jetbrains.intellij") version "1.3.0"
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}