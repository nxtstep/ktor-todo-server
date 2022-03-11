plugins {
    id("io.supersimple.plugin.ktor")
}

dependencies {
    implementation(project(":auth-service"))
    implementation(project(":jwt-auth-service"))
    implementation(project(":common"))
    implementation(project(":common-ktor"))
}
