plugins {
    application
    id("io.supersimple.plugin.ktor")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":todo-service"))
    implementation(project(":persistent-storage"))
    implementation(project(":persistent-storage-mysql"))
    implementation(project(":common-ktor"))
    implementation(project(":dev-auth-service"))
    implementation(project(":mysql-todo-service"))
    implementation(Dependencies.Logging.logback)
    implementation(Dependencies.Persistence.exposed)
}
