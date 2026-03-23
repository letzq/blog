# Repository Guidelines

## Project Structure & Module Organization
This repository is a Maven multi-module Spring Boot project targeting Java 21. The root [`pom.xml`](/D:/BLOG/Blog/pom.xml) aggregates:

- `admin/`: runnable Spring Boot app, with code under `admin/src/main/java`, config in `admin/src/main/resources`, and web assets in `admin/src/main/resources/static` and `templates`.
- `common/`: shared library module for reusable domain, utility, or configuration code.
- `target/`: generated build output. Do not edit committed files here by hand.

Add production code under `src/main/java` and tests under each module’s `src/test/java`.

## Build, Test, and Development Commands
Use the Maven wrapper from the repository root:

- `.\mvnw.cmd clean test`: compile all modules and run the JUnit test suite.
- `.\mvnw.cmd clean package`: build all modules and create the packaged artifact.
- `.\mvnw.cmd -pl admin spring-boot:run`: start the `admin` application locally on port `8080`.
- `.\mvnw.cmd -pl admin test`: run tests only for the application module.

Use `mvnw` on Unix-like systems and `mvnw.cmd` on Windows.

## Coding Style & Naming Conventions
Follow standard Spring Boot Java conventions:

- Use 4-space indentation and UTF-8 source files.
- Keep packages under `com.xy.blog` for app code and `com.xy` only when shared module boundaries require it.
- Name classes by role: `*Controller`, `*Service`, `*Config`, `*Repository`.
- Prefer constructor injection and keep configuration in `application.yml` or profile-specific overrides.

No formatter or linter is configured yet, so keep style consistent with existing Spring-generated structure and let the IDE organize imports before committing.

## Testing Guidelines
The project uses `spring-boot-starter-test` and `spring-security-test` with JUnit 5. Name test classes `*Tests` and mirror the production package structure. Favor focused slice tests where possible; reserve `@SpringBootTest` for integration coverage. Run `.\mvnw.cmd test` before opening a PR.

## Commit & Pull Request Guidelines
Git history is not available in this workspace, so use short imperative commit messages such as `Add OAuth login callback handler`. Keep one logical change per commit. PRs should include a brief summary, affected modules, test evidence, and screenshots when UI templates or static assets change.

## Security & Configuration Tips
`admin/src/main/resources/application.yml` currently contains local database and security credentials. Move real secrets to environment variables, a local override file, or your deployment secret store before sharing changes.
