# Docuffice Server (Quarkus Template)

This repository contains the **public template** for the Docuffice Server, built on Quarkus with a
reactive stack. All proprietary business logic and environment‑specific configuration files are
maintained in a separate **private** repository.

---

## Features

- **Reactive REST API** with [Quarkus REST](https://quarkus.io/guides/rest-json) & Jackson
- **Hibernate Reactive Panache** for non‑blocking ORM
- **SmallRye JWT** for issuing and validating JSON Web Tokens
- **Health checks** via SmallRye Health at `/health`
- **Flyway** database migrations + PostgreSQL (reactive & JDBC)
- **YAML‑based** configuration support
- **CDI** (Quarkus Arc) + **Elytron Security** + **JPA‑Reactive Security**
- **Code generation** with Lombok & MapStruct
- **Tests** with JUnit 5 & Rest‑Assured

---

## ️ Skills

| Layer            | Technology                                                                          |
|------------------|-------------------------------------------------------------------------------------|
| Framework        | Quarkus (Java 21)                                                                   |
| HTTP / JSON      | `quarkus-rest`, `quarkus-rest-jackson`, `quarkus-config-yaml`                       |
| Reactive Clients | `quarkus-rest-client-jackson`, `quarkus-reactive-pg-client`                         |
| Validation       | `quarkus-hibernate-validator`, `quarkus-jsonb`                                      |
| Persistence      | `quarkus-hibernate-orm`, `quarkus-hibernate-reactive-panache`                       |
| Database         | PostgreSQL (reactive + JDBC), Flyway                                                |
| Security         | `quarkus-elytron-security`, `quarkus-security-jpa-reactive`, `quarkus-smallrye-jwt` |
| Health / Metrics | `quarkus-smallrye-health`                                                           |
| Build / DI       | Gradle, Quarkus Plugin, CDI (Quarkus Arc)                                           |
| Codegen          | Lombok, MapStruct                                                                   |
| Testing          | `quarkus-junit5`, Rest‑Assured                                                      |

---

## Package Structure

```
com.example.${Domain}
├── application
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── mapper
│   ├── port
│   │   ├── in
│   │   └── out
│   └── service
├── domain
│   ├── mapper
│   ├── model
│   ├── repository
│   └── service
├── infrastructure
│   ├── adapter
│   │   ├── in
│   │   └── out
│   ├── external
│   └── persistence
│       ├── entity
│       └── repository
└── interfaces
    ├── exception
    └── rest

```

---

## Getting Started

1. **Clone** this template
   ```bash
   git clone https://github.com/lilstar-shin/docuffice-server.git
   cd docuffice-server
   ```

2. **Ignore profile‑specific configs**  
   We’ve already added
   ```gitignore
   application-*.yml
   ```  
   so any `application-<profile>.yml` files (e.g. `application-local.yml`) stay local.

3. **Create your own `application-local.yml`**
   ```bash
   cp src/main/resources/application-local.yml.example application-local.yml
   # edit DB credentials, JWT secret, etc.
   ```

4. **Build & Run in dev mode**
   ```bash
   ./gradlew clean build
   ./gradlew quarkusDev
   ```

5. **Visit**

- Health check:  `http://localhost:8080/health`
- OpenAPI UI:   `http://localhost:8080/q/openapi`
- (Add your own endpoints)

---

## Private Repository

> This public repo only contains the **skeleton** and dependency setup.  
> All feature implementation, business logic, and sensitive configuration files live in our *
*private** repository.

