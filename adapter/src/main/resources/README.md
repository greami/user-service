This module contains the Spring Boot application.

- application.yml configures H2 and the service port (8082 by default)
- UserApi.yaml is the OpenAPI specification file for the user service APIs
- schema.sql and data.sql are executed on startup (spring.sql.init.mode=always)

Next steps:
- adjust spring.boot.version in parent pom to latest if needed
- run `mvn -pl adapter clean spring-boot:run` to start
