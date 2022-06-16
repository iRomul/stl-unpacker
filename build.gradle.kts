import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version libs.versions.kotlin
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("edu.sc.seis.launch4j") version "2.5.1"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    sourceCompatibility = JavaVersion.VERSION_1_8
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
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.clikt)
}