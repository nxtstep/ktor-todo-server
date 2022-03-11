plugins {
    id("io.supersimple.plugin.ktor")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":todo-service"))
    implementation(project(":auth-service"))
    implementation(project(":persistent-storage-mysql"))
}
