FROM java:8

LABEL maintainer="xodns5977@naver.com"

VOLUME /tmp

EXPOSE 8081

ARG JAR_FILE=out/artifacts/LearningJPA_jar/

ADD ${JAR_FILE} LearningJPA.jar

ENTRYPOINT ["java","-jar","/tae-0.0.1-SNAPSHOT.jar"]