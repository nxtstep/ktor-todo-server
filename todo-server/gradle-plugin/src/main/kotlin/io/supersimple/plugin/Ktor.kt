package io.supersimple.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.provideDelegate

open class Ktor : Plugin<Project> {
    override fun apply(project: Project) {
        project.ktorDependencies()
    }
}

inline val org.gradle.plugin.use.PluginDependenciesSpec.supersimplektor: org.gradle.plugin.use.PluginDependencySpec
    get() = id("io.supersimple.plugin.ktor")


internal fun Project.ktorDependencies() = dependencies {
    val implementation by configurations
    implementation(Dependencies.Ktor.core)
    implementation(Dependencies.Ktor.netty)
    implementation(Dependencies.Ktor.serialization)
    implementation(Dependencies.Ktor.locations)
    implementation(Dependencies.Ktor.auth)
    implementation(Dependencies.Ktor.auth_jwt)

    val testImplementation by configurations
    testImplementation(kotlin("test"))

    testImplementation(Dependencies.Test.ktorServer)
    testImplementation(Dependencies.Test.kotlin)
}
