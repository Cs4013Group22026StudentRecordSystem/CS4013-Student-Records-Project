Design first is the right approach, and the deliverables list asks for exactly that (CRC cards and a UML class diagram). Here is a simple order to follow.

## Step 1: Read the requirements and mark them up
Go through the document with two highlighters:
- **Nouns** are candidate classes: student, programme, module, department, result, semester, transcript, faculty, exam board.
- **Verbs** are candidate behaviours: add student, register student on programme, record result, calculate QCA, generate transcript, identify progression status.

## Step 2: Write down your scope and assumptions
The document says you do *not* have to implement every regulation. Pick a representative subset, for example:
- The core features: add student, add programme, add module, register, record results, semester QCA, overall QCA, transcript, progression status.
- One or two extras, such as repeat decisions or awards classification.
- A grading scale and a progression rule (for example, a minimum QCA to progress), taken from Chapters 2 and 3 of the Handbook.

Write these assumptions in a short document now, because you have to submit them.

## Step 3: Choose your classes
From your nouns, you might get something like:
- `Student`, `Faculty` (both could extend an abstract `Person` or `User`)
- `Programme`, `Module`, `Department`
- `ModuleResult` (a student's grade in a module for a semester)
- `Semester`, `Transcript`, `ExamBoard`

Discard nouns that are only attributes. "Name" is a field of `Student`, not a class.

## Step 4: Assign responsibilities (CRC cards)
For each class, write a card with three parts:
- **Class name**
- **Responsibilities** (what it knows and does)
- **Collaborators** (which other classes it works with)

Example:

| Class: Student | |
|---|---|
| Responsibilities: store ID and name, hold registrations, hold results | Collaborators: Programme, ModuleResult, Transcript |

If a class has too many responsibilities, split it. If it has none, it probably isn't a class.

## Step 5: Apply the OO principles on purpose
The assignment says to show these, so decide where each one goes:
- **Encapsulation:** private fields with getters and setters, and validation inside the class (for example, a grade can't be negative).
- **Inheritance:** `Person` → `Student` / `Faculty`. Also `Programme` → `UndergraduateProgramme` / `TaughtPostgraduate` / `ResearchProgramme`.
- **Abstraction:** an abstract `Programme` class or a `ProgressionRule` interface that says *what* must be done without saying *how*.
- **Polymorphism:** each programme type implements `calculateQCA()` or `checkProgression()` in its own way, and your code calls the same method on all of them.

## Step 6: Draw the UML class diagram
Draw each class with its fields and methods, then connect them:
- **Association:** a Student registers on a Programme.
- **Aggregation/composition:** a Programme has Modules.
- **Inheritance** and **interface implementation** arrows.
- **Multiplicity** on the lines (1, 0..*, and so on).

You can use draw.io or any similar tool.

## Step 7: Design the CSV storage
The system stores everything as CSV, so decide the files and columns now, for example:
- `students.csv`: studentId, name, programmeCode
- `programmes.csv`: code, name, type, durationYears
- `modules.csv`: moduleCode, title, credits
- `results.csv`: studentId, moduleCode, semester, grade

Keep file reading and writing in separate classes (like `CsvStudentRepository`) so your model classes don't know about files. This is also good OO design.

## Step 8: Plan the command-line interface
Sketch the menus and which role sees what:
- Student: view my transcript
- Faculty: look up transcripts, submit results
- Department: run an exam board

Each menu option should call a method on a class you already designed. If you can't tell which class handles an option, you've found a gap in your design.

## Step 9: Split the work and plan testing
Every team member must contribute substantially to the *code*, so divide by layers or features, such as model classes, QCA and progression logic, CSV and CLI. Decide what you will test (for example, QCA calculations with known answers) and prepare test CSV data. Commit to GitHub regularly from day one, because the commit history is assessed.

## Step 10: Only then start coding
Build in small pieces. For example, do `Student`, `Module`, `Programme` and the CSV loading first. Then add results and QCA, then progression, then the CLI.

If you'd like, we can do Steps 1 to 3 together next. Tell me which grading scale and progression rule you want to use, or paste the relevant part of the Handbook, and I'll help you draft the class list and a first CRC card or two.

Your design steps are above. I'll also keep the project context so we can pick this up in later chats.