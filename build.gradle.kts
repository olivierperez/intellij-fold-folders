import org.jetbrains.compose.compose

plugins {
    id("org.jetbrains.intellij") version "1.0"
    kotlin("jvm")
    java
    id("org.jetbrains.compose")
}

group = "fr.o80"
version = "1.5"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
}

tasks {
    patchPluginXml {
        changeNotes.set("""
            Initial release of the plugin.
            """.trimIndent())
        sinceBuild.set("192")
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}