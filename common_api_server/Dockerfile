FROM openjdk:11
COPY . /usr/src/ourapt_api_server
WORKDIR /usr/src/ourapt_api_server
CMD ./gradlew bootJar && java -jar ./build/libs/ourapt-0.0.1-SNAPSHOT.jar
