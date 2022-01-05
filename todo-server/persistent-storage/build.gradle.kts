dependencies {
    implementation(project(":common"))
    implementation(Dependencies.Persistence.h2)
    implementation(Dependencies.Persistence.mysql)
    implementation(Dependencies.Persistence.hikari)
    implementation(Dependencies.Persistence.exposed)
}
