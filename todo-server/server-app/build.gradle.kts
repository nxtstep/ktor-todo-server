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
    implementation(Dependencies.Logging.logback)
    implementation(Dependencies.DI.kodein)
    implementation(Dependencies.Persistence.exposed)
}
