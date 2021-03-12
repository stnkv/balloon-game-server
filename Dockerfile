FROM gradle:jdk15 as build

RUN mkdir -p /app
WORKDIR /app

COPY src/main/java/ru/stnkv ./
RUN gradle bootJar

###
###

FROM openjdk:15

COPY --from=build /app/build/libs/* /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","/app/stnkv-backend.jar"]