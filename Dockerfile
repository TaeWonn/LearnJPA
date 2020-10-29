FROM java:8

LABEL maintainer="xodns5977@naver.com"

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=target/Tae-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} tae.jar

ENTRYPOINT ["java","-jar","/tae.jar"]