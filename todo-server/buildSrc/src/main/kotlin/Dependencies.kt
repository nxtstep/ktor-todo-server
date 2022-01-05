object Dependencies {
    object Versions {
        const val kotlin = "1.5.20"
        const val kotlinCoroutines = "1.5.2"
        const val ktor = "1.6.7"
        const val logback = "1.2.10"
        const val kodein = "7.9.0"
        const val exposed = "0.17.14"
        const val mysql = "8.0.25"
        const val hikari = "5.0.0"
        const val h2 = "2.0.204"
    }

    object Kotlin {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    }

    object Ktor {
        const val core = "io.ktor:ktor-server-core:${Versions.ktor}"
        const val netty = "io.ktor:ktor-server-netty:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization:${Versions.ktor}"
        const val locations = "io.ktor:ktor-locations:${Versions.ktor}"
        const val auth = "io.ktor:ktor-auth:${Versions.ktor}"
        const val auth_jwt = "io.ktor:ktor-auth-jwt:${Versions.ktor}"
    }

    object Logging {
        const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
    }

    object Persistence {
        const val mysql = "mysql:mysql-connector-java:${Versions.mysql}"
        const val h2 = "com.h2database:h2:${Versions.h2}"
        const val hikari = "com.zaxxer:HikariCP:${Versions.hikari}"
        const val exposed = "org.jetbrains.exposed:exposed:${Versions.exposed}"
    }

    object DI {
        const val kodein = "org.kodein.di:kodein-di-framework-ktor-server-controller-jvm:${Versions.kodein}"
    }

    object Test {
        const val ktorServer = "io.ktor:ktor-server-tests:${Versions.ktor}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-test"
    }
}
