
rootProject.name = "todo-server"

include(
  "auth-service",
  "dev-auth-service",
  "jwt-auth-service",
  "common",
  "common-ktor",
  "persistent-storage",
  "server-app",
  "todo-service",
  "persistent-storage-h2",
  "persistent-storage-mysql",
  "mysql-todo-service"
)

project(":persistent-storage-h2").projectDir = file("persistent-storage/persistent-storage-h2")
project(":persistent-storage-mysql").projectDir = file("persistent-storage/persistent-storage-mysql")
project(":dev-auth-service").projectDir = file("auth-service/dev-auth-service")
project(":jwt-auth-service").projectDir = file("auth-service/jwt-auth-service")
project(":mysql-todo-service").projectDir = file("todo-service/mysql-todo-service")
