user-service

Modules:
- domain: core domain records and ports
- application: use-cases and mappers
- adapter: Spring Boot application, adapters, infra

Run:
  mvn -T 1C clean install
  mvn -pl adapter spring-boot:run
