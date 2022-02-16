FROM openjdk:17
ADD target/keycloak-admin-adapter-0.0.1-SNAPSHOT.jar adapter.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /adapter.jar" ]