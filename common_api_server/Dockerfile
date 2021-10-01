FROM openjdk:11
COPY . /usr/src/ume_apartment_api_server
WORKDIR /usr/src/ume_apartment_api_server
CMD ./gradlew bootJar && java -jar ./build/libs/umeapartment-0.0.1-SNAPSHOT.jar
