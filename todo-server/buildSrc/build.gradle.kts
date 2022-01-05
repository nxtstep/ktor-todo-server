plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir(file("../gradle-plugin/src/main/kotlin"))
        resources.srcDir(file("../gradle-plugin/src/main/resources"))
    }
}

dependencies {
    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
}
