plugins {
    id("org.jetbrains.intellij") version "1.0"
    kotlin("jvm")
    java
}

group = "fr.o80"
version = "1.6"

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
            <h1>1.6</h1>
            <ul>
                <li>Make the UI fits IntelliJ, and drop Jetbrains Compose</li>
                <li>Validation of Regex format</li>
            </ul>
            <h1>1.0 to 1.5</h1>
            <ul>
                <li>Initial release of the plugin</li>
            </ul>
            """.trimIndent())
        sinceBuild.set("192")
        untilBuild.set("213")
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}