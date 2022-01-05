plugins {
    id("io.supersimple.plugin.ktor")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":common-ktor"))
    implementation(project(":persistent-storage"))
    implementation(Dependencies.DI.kodein)
    implementation(Dependencies.Persistence.exposed)
}
