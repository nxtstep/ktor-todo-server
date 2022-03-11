FROM gradle:7.1 AS build
WORKDIR /
COPY . .
RUN ./gradlew installDist

FROM openjdk:11
RUN mkdir app
COPY --from=build server-app/build/install/server-app /app
WORKDIR /app
CMD ["./bin/server-app"]
