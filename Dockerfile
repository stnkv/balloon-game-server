FROM gradle:jdk15 as build

RUN mkdir -p /app
WORKDIR /app

COPY ./ ./
RUN gradle bootJar

###
###

FROM openjdk:15

COPY --from=build /app/build/libs/*.jar /app/app.jar
WORKDIR /app

ENTRYPOINT ["java","-jar","/app/app.jar"]