user-service

Modules:
- domain: core domain records and ports
- application: use-cases and mappers
- adapter: Spring Boot application, adapters, infra

UML diagrams (PlantUML sources):
- [Class diagram](docs/uml/class-diagram.puml)
- [Use-case diagram](docs/uml/use-case-diagram.puml)
- [Database ER diagram](docs/uml/database-er-diagram.puml)

Run:
  mvn -T 1C clean install
  mvn -pl adapter spring-boot:run
