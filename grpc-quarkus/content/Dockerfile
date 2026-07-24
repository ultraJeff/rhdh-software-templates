## Stage 1: Build
FROM registry.access.redhat.com/ubi8/openjdk-21:latest AS build
USER root
COPY --chown=185 . /work
WORKDIR /work
RUN mvn -B package -DskipTests

## Stage 2: Runtime
FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:latest
ENV LANGUAGE='en_US:en'

COPY --from=build --chown=185 /work/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /work/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /work/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /work/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080 9000
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]
