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

            library("clikt", "com.github.ajalt.clikt:clikt:3.5.0")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            library("kotlin-stdlib-jdk7", "org.jetbrains.kotlin", "kotlin-stdlib-jdk7").versionRef("kotlin")
            library("kotlin-stdlib-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
        }
    }
}