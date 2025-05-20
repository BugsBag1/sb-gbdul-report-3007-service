FROM nexus-dev.eub.kz:8088/library/maven:3.8.6-openjdk-11 AS build

COPY src /home/app/src
COPY pom.xml /home/app
COPY settings.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -Dmaven.wagon.http.ssl.insecure=true -Dmaven.test.skip=true -s /home/app/settings.xml

FROM nexus-dev.eub.kz:8088/library/maven:3.9.9-eclipse-temurin-11-alpine

ENV http_proxy='http://proxy.eub.kz:8080'
ENV HTTP_PROXY='http://proxy.eub.kz:8080'
ENV HTTPS_PROXY='http://proxy.eub.kz:8080'
ENV https_proxy='http://proxy.eub.kz:8080'
ENV no_proxy='localhost,127.0.0.1,eub.kz,*.eub.kz, egov.kz'
ENV NO_PROXY='localhost,127.0.0.1,eub.kz,*.eub.kz, egov.kz'

COPY eubRootCA.cer /usr/share/ca-certificates/eubRootCA.cer
COPY eubIssuerCA.cer  /usr/share/ca-certificates/eubIssuerCA.cer
COPY egov.kz.cer /usr/share/ca-certificates/egov.kz.cer

RUN echo eubRootCA.cer >> /etc/ca-certificates.conf
RUN echo egov.kz.cer >> /etc/ca-certificates.conf
RUN update-ca-certificates
RUN keytool -import -trustcacerts -alias eubRootCA -file /usr/share/ca-certificates/eubRootCA.cer -keystore /opt/java/openjdk/lib/security/cacerts -storepass changeit -noprompt
RUN keytool -import -trustcacerts -alias eubIssuerCA -file /usr/share/ca-certificates/eubIssuerCA.cer -keystore /opt/java/openjdk/lib/security/cacerts -storepass changeit -noprompt
RUN keytool -import -trustcacerts -alias egov -file /usr/share/ca-certificates/egov.kz.cer -keystore /opt/java/openjdk/lib/security/cacerts -storepass changeit -noprompt

COPY gost.p12 /app/cert/

WORKDIR /tmp

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

EXPOSE 8080

ENV TZ=Asia/Almaty
ENTRYPOINT ["java","-Djava.awt.headless=true","-jar","/usr/local/lib/app.jar"]
