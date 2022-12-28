FROM maven:3.6.3-openjdk-8 AS build 
ARG MVN_PROFILE 
WORKDIR /app 
COPY . /app/ 
COPY ./settings.xml /usr/share/maven/ref/ 
RUN mvn -B -f /app/pom.xml -s /usr/share/maven/ref/settings.xml clean -P${MVN_PROFILE} -DskipTests 
RUN mvn -B -f /app/pom.xml -s /usr/share/maven/ref/settings.xml install -P${MVN_PROFILE} -DskipTests 
RUN mvn -B -f /app/pom.xml -s /usr/share/maven/ref/settings.xml package -P${MVN_PROFILE} -DskipTests
RUN ls /app 
FROM tomcat:9.0 
COPY --from=build /app/clinic-stack-prod/target/clinic-stack-prod-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/clinic-stack-server.war 
RUN sed -i 's/port="8080"/port="8077"/' /usr/local/tomcat/conf/server.xml 
EXPOSE 8077 
CMD ["catalina.sh", "run"] 