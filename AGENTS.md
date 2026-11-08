# AGENTS.md

Context and conventions for anyone (human or AI agent) working in this repository.

## Shared agent context: `.github/agents/`

[.github/agents/](.github/agents) is a **shared, team-visible** directory: it holds the authoritative
course materials and shared design references for this project, not application code.
All four team members and their agents read the same files from here.

| File | Purpose |
| --- | --- |
| `CS4013 Software Development ProjectAY202627.md` / `.pdf` | The assignment specification: required features, deliverables, grading rules, interview rules. |
| `Handbook of Academic Regulations and Procedures 2026-27 Chapter 2-3.md` / `.pdf` | UL's actual marking/progression regulations (Chapter 2 "Marks and Standards", Chapter 3 "Progression"). This is the source of truth for QCA, progression and award rules. |
| `UML-diagram.md` | Shared Mermaid class diagram describing the planned model, services, repositories, and their relationships. |

Each document exists as both `.md` and `.pdf`. **They are the same content** — the
`.md` is the extracted text and is much cheaper to read; open the `.pdf` only when
formatting, tables or figures matter.

### Rule: when anything is unclear, read these first

If a request, ticket, comment or existing code is ambiguous about **what the system
must do**, do not guess. Open the relevant document above and cite the section you
relied on (for example: "Handbook 2.1.3", "Spec — Sample additional features").

Typical things to resolve there before writing code:

- Grading scale, grade bands, and how QCA is calculated
- Progression / repeat / link-in rules and what counts as a fail
- Award classification thresholds
- Which features are *required* vs. *suggested*, and which deliverables are graded
- Assumptions: the spec says only a representative subset of rules must be
  implemented — any assumption you make must be documented, not invented silently

If the documents genuinely do not answer the question, ask the team rather than
inventing a regulation.

### UML design reference

Use [`.github/agents/UML-diagram.md`](.github/agents/UML-diagram.md) as the shared
class-design reference. It describes the intended architecture and may include
planned or placeholder classes, so it is not proof that a feature is implemented.
Update it when the agreed design changes, and keep it consistent with the code
when implementation is completed. Store application code under `src/`, not in
`.github/agents/`.

## Repository layout

- All Java code lives in the Maven source tree: `src/main/java` and `src/test/java`.
  There is no `Code/` directory — do not create one.
- All CSV data (including mock/test data) lives in `data/`. There is no `CSV/` directory.
- `CRC/` holds the CRC cards deliverable.
- `target/` is build output and is git-ignored.

## Build and test

Java 17 + the Maven Wrapper. Use `./mvnw` (`mvnw.cmd` on Windows) so everyone builds
with the same pinned Maven version (3.10.0) — a local `mvn` may differ.

```bash
./mvnw test       # compile and run the tests
./mvnw package    # build target/student-records-1.0-SNAPSHOT.jar (main class: app.Main)
```

CI ([.github/workflows/maven.yml](.github/workflows/maven.yml)) runs
`./mvnw --batch-mode --no-transfer-progress clean test-compile` on every push and on
pull requests to `main`: it must stay green, so make sure the code compiles (main
**and** test sources) before pushing.

## Conventions

- Keep one concern per commit, with a conventional prefix (`feat:`, `fix:`, `docs:`,
  `test:`, `chore:`). The spec grades contribution history, so avoid large
  end-of-project dump commits.
- Do not commit build output (`target/`) or course PDFs/MDs outside `.github/agents/`.
- Every behaviour-affecting class is expected to be documented well enough to be
  presented at the project interview.
