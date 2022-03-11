dependencies {
    implementation(project(":common"))
    api(project(":persistent-storage"))
    implementation(Dependencies.Persistence.mysql)
    implementation(Dependencies.Persistence.hikari)
}
