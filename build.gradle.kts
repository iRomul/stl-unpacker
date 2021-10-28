import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("edu.sc.seis.launch4j") version "2.5.1"
}

group = "io.github.iromul.utils"
version = "1.0-SNAPSHOT"

java {
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

application {
    mainClass.set("io.github.iromul.utils.stlunpacker.Stl_unpackerKt")
}

repositories {
    mavenCentral()
}

tasks.shadowJar {
    manifest {
        attributes("Main-Class" to "io.github.iromul.utils.stlunpacker.Stl_unpackerKt")
    }
}

launch4j {
    outfile = "stlunpacker.exe"
    mainClassName = "io.github.iromul.utils.stlunpacker.Stl_unpackerKt"
    icon = "$projectDir/icons/gear_win_multi.ico"
    fileDescription = "Simple zip unpack utility for stl files"
    copyConfigurable = emptyList<Any?>()
    jarTask = project.tasks.shadowJar.get()
    headerType = "console"
    textVersion = project.version.toString()
    copyright = "Roman Komarevtsev"

}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
}