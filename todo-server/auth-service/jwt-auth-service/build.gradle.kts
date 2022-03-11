plugins {
    id("io.supersimple.plugin.ktor")
}

dependencies {
    implementation(project(":auth-service"))
}
