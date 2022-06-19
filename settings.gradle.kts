@file:Suppress("UnstableApiUsage")

rootProject.name = "stl-unpacker"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("kotlin", "1.7.0")
            version("ktor", "2.0.2")

            library("clikt", "com.github.ajalt.clikt:clikt:3.5.0")
            library("jsoup", "org.jsoup:jsoup:1.15.1")
            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:5.8.2")
            library("kotlin-logging", "io.github.microutils:kotlin-logging:2.1.23")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            library("kotlin-stdlib-jdk7", "org.jetbrains.kotlin", "kotlin-stdlib-jdk7").versionRef("kotlin")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
            library("ktor-client-apache", "io.ktor", "ktor-client-apache").versionRef("ktor")
            library("slf4j-simple", "org.slf4j:slf4j-simple:1.7.36")
        }
    }
}