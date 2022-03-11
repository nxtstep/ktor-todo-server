import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("jvm") version Dependencies.Versions.kotlin
    kotlin("plugin.serialization") version Dependencies.Versions.kotlin
}

group = "io.supersimple.todo"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.majorVersion

            // opt into experimental APIs
            freeCompilerArgs.plus("-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi")
            freeCompilerArgs.plus("-Xopt-in=io.ktor.locations.KtorExperimentalLocationsAPI")
        }
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        val implementation by configurations
        implementation(kotlin("stdlib-jdk8"))
    }
}
