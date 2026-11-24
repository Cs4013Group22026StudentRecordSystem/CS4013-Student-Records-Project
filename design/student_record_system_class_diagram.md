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
        -Program program
        -String progressionStatus
        -int currentSemester
        -ArrayList~double~ semesterQCAs
        -double currentOverallQCA
        +Student(name, studentId)
        +registerOnProgram(program) void
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
        +Faculty(name, personId)
        +chooseAModuleToTeach(module) void
        +addStudentOnModule() void
        +removeStudentFromModule() void
        +setResult(student, grade) void
        +generateTranscript() String
    }

    class Module {
        -String code
        -String name
        -Grade grade
        +Module(code, name)
        +setGrade(Grade) void
        +getGradeInQCA() double
        +copy() Module
    }

    class Program {
        -String name
        -ArrayList~Module~ allModules
        -ArrayList~Module~ currentFiveModules
        +Program(name)
        +addModule(module) void
        +setCurrentFiveModules() void
        +getCurrentFiveModules() ArrayList~Module~
        +copy() Program
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
        NG
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
        +update() void
    }

    class Storable {
        <<interface>>
        +read() void
        +write() void
        +update() void
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
    Faculty ..> Storable : writes results.csv

    %% Department holds (aggregation)
    Department "1" o--> Faculty
    Department "1" o--> Student
    Department "1" o--> Program
    Department "1" o--> Module

    %% Faculty
    Faculty "1" o--> Module
    Faculty "1" o--> Student

    %% Container model: Student[Program[Module[Grade]]]
    Student "1" *--> Program
    Program "1" *--> Module
    Module "1" *--> Grade
```

## Container model

Each `Student` owns its own copy of a `Program`, which owns its own copies of the `Module` objects, each holding its own `Grade`:

`Student[Program[Module1[Grade], Module2[Grade], ...]]`

- `Department` stores the `Program` and `Module` objects as grade-free templates.
- `Department.register(student, program)` calls `program.copy()`, which calls `copy()` on each module (grades start as `NG`), and gives the copy to the student.
- `Faculty.setResult(student, grade)` finds the student's own copy of the module (matched by module `code`) and sets the grade there.

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
| Department | Aggregation | Program | 1 |
| Department | Aggregation | Module | 1 |
| Faculty | Aggregation | Module | 1 |
| Faculty | Aggregation | Student | 1 |
| Student | Composition | Program | 1 |
| Program | Composition | Module | 1 |
| Module | Composition | Grade | 1 |