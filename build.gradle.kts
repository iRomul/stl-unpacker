import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version libs.versions.kotlin
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("edu.sc.seis.launch4j") version "2.5.1"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("io.github.iromul.diy.tools.stlunpacker.Stl_unpackerKt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
}

tasks.shadowJar {
    manifest {
        attributes("Main-Class" to "io.github.iromul.diy.tools.stlunpacker.Stl_unpackerKt")
    }
}

launch4j {
    outfile = "stlunpacker.exe"
    mainClassName = "io.github.iromul.diy.tools.stlunpacker.Stl_unpackerKt"
    icon = "$projectDir/icons/gear_win_multi.ico"
    fileDescription = "Simple zip unpack utility for stl files"
    copyConfigurable = emptyList<Any?>()
    jarTask = project.tasks.shadowJar.get()
    headerType = "console"
    textVersion = project.version.toString()
    copyright = "Roman Komarevtsev"

}

dependencies {
    implementation(libs.clikt)
    implementation(libs.jsoup)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.ktor.client.apache)
    implementation(libs.slf4j.simple)
    testImplementation(libs.junit.jupiter)
}