FROM openjdk:8 as builder

WORKDIR /usr/app

RUN apt update -y && \
    apt upgrade -y && \
    apt install -y maven

COPY . .

RUN mvn clean install

FROM openjdk:8-alpine

WORKDIR /usr/app

COPY --from=builder /usr/app/target/*.jar ./store.jar

EXPOSE 8080

CMD ["java", "-jar", "store.jar"]
