dependencies {
    implementation(project(":common"))
    api(project(":persistent-storage"))
    implementation(Dependencies.Persistence.h2)
}
