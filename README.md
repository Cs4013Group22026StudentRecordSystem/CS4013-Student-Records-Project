# CS4013-Student-Records-Project

## Project Structure

All Java source code lives in the Maven source tree (`src/main/java`). There is no
separate `Code/` directory, and all CSV data (including mock/test data) lives in
`data/` — there is no separate `CSV/` directory.

```text
.
├── pom.xml                     # Maven build (Java 17, JUnit 5, executable JAR)
├── mvnw / mvnw.cmd             # Maven Wrapper launchers (no local Maven needed)
├── .mvn/wrapper/               # Maven Wrapper configuration (pinned Maven 3.10.0)
├── README.md
├── AGENTS.md                   # Conventions and shared agent context (see below)
├── .gitattributes              # Line-ending normalisation
├── .editorconfig               # Editor defaults (indent, charset, EOL)
├── .gitignore
├── .github/
│   ├── agents/                 # Shared course materials: spec + UL handbook
│   └── workflows/              # GitHub Actions CI
├── CRC/                        # CRC cards (documentation deliverable)
├── data/                       # CSV data files, including mock/test data
└── src
    ├── main
    │   ├── java
    │   │   ├── app/            # Application entry point
    │   │   ├── model/          # Domain models and enums
    │   │   ├── presentation/   # Command-line interface and input
    │   │   ├── repository/     # Student data repositories
    │   │   └── service/        # QCA, award, and progression logic
    │   └── resources/          # Application resources
    └── test
        └── java
            ├── model/
            ├── presentation/
            ├── repository/
            └── service/
```

## Shared agent context

[.github/agents/](.github/agents) holds the authoritative course materials shared by the
whole team:

- `CS4013 Software Development ProjectAY202627.md` / `.pdf` — the assignment specification
- `Handbook of Academic Regulations and Procedures 2026-27 Chapter 2-3.md` / `.pdf` — the
  UL marking and progression regulations the system must implement

The `.md` and `.pdf` copies of each document have the same content; prefer the `.md`.
If any requirement is unclear, consult these documents before deciding — see
[AGENTS.md](AGENTS.md) for the details.

## TODO

- [ ] Add an example JUnit 5 test under `src/test/java/model/` (e.g. `GradeTest`)
      that asserts something trivial about a `Grade`, so `mvn test` demonstrably
      executes at least one test instead of silently passing with zero tests.

## Build and Run

Requires JDK 17. Maven itself is optional: use the wrapper (`./mvnw`, or
`mvnw.cmd` on Windows) and it downloads the pinned Maven 3.10.0 on first run.

```bash
./mvnw test         # compile and run the unit tests
./mvnw package      # build the executable JAR (main class: app.Main)
java -jar target/student-records-1.0-SNAPSHOT.jar
```

Without the wrapper, the same commands work with a local `mvn`.
