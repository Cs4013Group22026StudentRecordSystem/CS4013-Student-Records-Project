# AGENTS.md

Context and conventions for anyone (human or AI agent) working in this repository.

## Shared agent context: `.github/agents/`

[.github/agents/](.github/agents) is a **shared, team-visible** directory: it holds the authoritative
course materials and shared design references for this project, not application code.
All four team members and their agents read the same files from here.

> The agreed design now lives in `.github/agents/design/`. Code is being refactored to match it.
> `.github/agents/deprecated-design/` is kept for reference only — do not implement against it.

| File | Purpose |
| --- | --- |
| `CS4013 Software Development ProjectAY202627.md` / `.pdf` | The assignment specification: required features, deliverables, grading rules, interview rules. |
| `Handbook of Academic Regulations and Procedures 2026-27 Chapter 2-3.md` / `.pdf` | UL's actual marking/progression regulations (Chapter 2 "Marks and Standards", Chapter 3 "Progression"). This is the source of truth for QCA, progression and award rules. |
| `design/student_records_class_diagram.md` | **Design of record.** Shared Mermaid class diagram describing the model, services and their relationships. |
| `design/Design_creation_plan.md` | Early planning notes. Guidance only — where it disagrees with the diagram above, we will follow the `design/student_records_class_diagram.md` to develop, when we meet some problem then we can discuss and refine it. |
| `deprecated-design/UML-diagram.md` | Superseded diagram, kept for reference during the refactor. Read-only. |

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

Use [`.github/agents/design/student_records_class_diagram.md`](.github/agents/design/student_records_class_diagram.md)
as the shared class-design reference. It describes the intended architecture and may
include planned or placeholder classes, so it is not proof that a feature is
implemented. Update it when the agreed design changes, and keep it consistent with
the code when implementation is completed. Store application code under `src/`, not
in `.github/agents/`.

## Repository layout

- All Java code lives in the Maven source tree: `src/main/java` and `src/test/java`.
  There is no `Code/` directory — do not create one.
- All CSV data (including mock/test data) lives in `data/`. There is no `CSV/` directory.
- `CRC/` holds the CRC cards deliverable.
- `target/` is build output and is git-ignored.
- `docs/apidocs/` is the generated Javadoc site, rebuilt by `./mvnw verify` and
  **git-ignored** — it is produced and published by CI, never committed.
- `docs/index.html` is the only tracked file under `docs/`: a hand-written landing
  page that redirects to `docs/apidocs/index.html`.

## Build and test

Java 17 + the Maven Wrapper. Use `./mvnw` (`mvnw.cmd` on Windows) so everyone builds
with the same pinned Maven version (3.10.0) — a local `mvn` may differ.

```bash
./mvnw test       # compile main and test sources, then run the JUnit tests
./mvnw verify     # the above, plus build the JAR and regenerate the Javadoc
./mvnw package    # produces the JAR only; no Javadoc, no separate plugin
```

The lifecycle means `test` is a subset of `verify`, which is a subset of `install`.
`test` writes only `target/classes`, `target/test-classes` and
`target/surefire-reports/`; `verify` additionally produces
`target/student-records-1.0-SNAPSHOT.jar` and the Javadoc site in `docs/apidocs/`.
Javadoc is bound to `verify`, so a doc comment that names a parameter which does not
exist (or otherwise breaks `{@link}`/HTML) fails the build; missing comments are
warnings only. No static analysis plugin is bound. `notimestamp` keeps unchanged pages
byte-identical, so a rebuild with no source changes produces identical output.

CI uses two workflows, both running
`./mvnw --batch-mode --no-transfer-progress clean verify`:

- `maven.yml` — every push and every pull request to `main`. Because it runs the tests
  rather than only compiling, CI fails on a failing assertion as well as on a compile
  error.
- `pages.yml` — pushes to `main`; after the build it uploads `docs/` as the Pages
  artifact and deploys it. Generated docs are never committed, so the published site
  always matches the branch.

Run `./mvnw verify` locally before pushing.

## Conventions

- Keep one concern per commit, with a conventional prefix (`feat:`, `fix:`, `docs:`,
  `test:`, `chore:`). The spec grades contribution history, so avoid large
  end-of-project dump commits.
- Do not commit build output (`target/`) or course PDFs/MDs outside `.github/agents/`.
- Every behaviour-affecting class is expected to be documented well enough to be
  presented at the project interview.
