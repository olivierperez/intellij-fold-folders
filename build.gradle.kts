plugins {
    id("org.jetbrains.intellij") version "1.0"
    kotlin("jvm")
    java
}

group = "fr.o80"
version = "1.5"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

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