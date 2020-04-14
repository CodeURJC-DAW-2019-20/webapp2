FROM openjdk:8-alpine

COPY ./backend/tenniShip/target/pruebaPractica-0.0.1-SNAPSHOT.jar ./tennishipjar.jar

COPY ./backend/tenniShip/images ./images

CMD ["java", "-jar", "tennishipjar.jar"]