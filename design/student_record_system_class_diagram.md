# CS4013 Student Record System – UML Class Diagram

```mermaid
classDiagram
    direction TB

    class Main {
        +run(UserInterface) void
    }

    class Department {
        -ArrayList~Student~ students
        -ArrayList~Faculty~ professors
        -ArrayList~Program~ programs
        -ArrayList~Module~ modules
        +Department()
        +addStudent(student) void
        +addFaculty(faculty, module) void
        +addProgram(program) void
        +addModule(module) void
        +register(student, program) void
        +calculateSemesterQCA(student) void
        +calculateOverallQCA(student) void
        +identifyProgressionStatus(student) void
        +progress(student) void
    }

    class Person {
        <<abstract>>
        -String name
        -String personId
        +generateTranscript()* String
    }

    class Student {
        -Programme programme
        -String progressionStatus
        -int currentYear
        -int currentSemester
        +Student(name, studentId)
        +registerOnProgram(programme) void
        +receiveGrade(grade, module) void
        +calculateSemesterQCA() double
        +calculateOverallQCA() double
        +identifyProgressionStatus() String
        +progress() void
        +generateTranscript() String
    }

    class Faculty {
        -Module module
        -ArrayList~Student~ students
        +Faculty(name, personId, storable)
        +getModule() Module
        +chooseAModuleToTeach(module) void
        +setResult(student, grade) void
        +getStudents() ArrayList~Student~
        +generateTranscript() String
    }

    class Module {
        -String name
        -String code
        -Grade grade
        +Module(code, name)
        +setGrade(Grade) void
        +copy() Module
    }

    class Programme {
        -String code
        -String name
        -ArrayList~Module~ modules
        -Map~String, Integer~ moduleSemesters
        +Programme(code, name)
        +addModule(module, semester) void
        +getModules() ArrayList~Module~
        +getModuleSemester(moduleCode) int
        +copy() Programme
    }

    class Grade {
        <<enumeration>>
        A1
        A2
        B1
        B2
        B3
        C1
        C2
        C3
        D1
        F
        P
        NG
        N
        -double qvp
        +getQvp() double
    }

    class CLI_class {
        +PlaceHolder() void
    }

    class UserInterface {
        <<interface>>
        +PlaceHolder() void
    }

    class CSV_handler {
        +read() void
        +write() void
        +update(student, module) void
    }

    class Storable {
        <<interface>>
        +read() void
        +write() void
        +update(student, module) void
    }

    %% Inheritance
    Person <|-- Student : extends
    Person <|-- Faculty : extends

    %% Interface implementation
    UserInterface <|.. CLI_class : implements
    Storable <|.. CSV_handler : implements

    %% Dependencies
    Main --> Department
    Main ..> UserInterface : uses
    Faculty ..> Storable : writes module result CSV

    %% Department holds (aggregation)
    Department "1" o--> Faculty
    Department "1" o--> Student
    Department "1" o--> Programme
    Department "1" o--> Module

    %% Faculty
    Faculty "1" o--> Module
    Faculty "0..*" -- "0..*" Student : grades

    %% Each student owns a programme/module copy with its recorded grades.
    Student "1" *--> Programme
    Programme "1" *--> Module
```

## Container model

Each `Student` owns a copy of their `Programme`, which owns copies of its `Module` objects:

`Student[Programme[Module1[Grade?], Module2[Grade?], ...]]`

- `CSV_handler` stores the shared programme/module catalogue as templates.
- `Student.registerOnProgram(programme)` copies the programme, its module-semester mapping, and its modules. A copied module starts with a null grade; an explicit `NG` is a recorded grade.
- `Faculty.setResult(student, grade)` checks that the student's programme contains the taught module, then records the grade on that student's module copy.
- The module result CSV stores one current grade per student and module; there is no result ID, attempt history, or result-specific semester.
- Semester numbers increase across the whole course. `calculateSemesterQCA()` uses `currentSemester` and the copied programme's module-semester mapping; `generateTranscript()` lists recorded grades with their curriculum semesters.
- Handbook `NG` has 0.00 QPV and is included in QCA; ungraded null grades are excluded. Pass/fail `P` and `N` are non-quality grades.

## Relationship summary

| From | Relationship | To | Multiplicity (at source) |
|---|---|---|---|
| Student | Inheritance (extends) | Person | – |
| Faculty | Inheritance (extends) | Person | – |
| CLI_class | Realization (implements) | UserInterface | – |
| CSV_handler | Realization (implements) | Storable | – |
| Main | Association (uses) | Department | – |
| Main | Dependency (uses) | CLI_class | – |
| Faculty | Dependency (uses) | Storable | – |
| Department | Aggregation | Faculty | 1 |
| Department | Aggregation | Student | 1 |
| Department | Aggregation | Programme | 1 |
| Department | Aggregation | Module | 1 |
| Faculty | Aggregation | Module | 1 |
| Faculty | Association | Student | 0..* |
| Student | Composition | Programme | 1 |
| Programme | Composition | Module | 1 |