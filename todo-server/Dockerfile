FROM gradle:7.1 AS build
WORKDIR /
COPY . .
RUN ./gradlew installDist

FROM openjdk:11
RUN mkdir app
COPY --from=build /build/install/todo-server /app
WORKDIR /app
CMD ["./bin/todo-server"]
