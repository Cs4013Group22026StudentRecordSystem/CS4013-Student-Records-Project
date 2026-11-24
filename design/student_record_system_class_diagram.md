# CS4013 Student Record System – UML Class Diagram

```mermaid
classDiagram
    direction TB

    class Main {
        +run(CLI_class) void
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
        +progress(student)
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
    }

    class Program {
        -String name
        -ArrayList~Module~ allModules
        -ArrayList~Module~ currentFiveModules
        +Program(name)
        +addModule(module) void
        +setCurrentFiveModules() void
        +getCurrentFiveModules() ArrayList~Module~
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

    }

    interface UserInterface {

    }

    class CSV_handler {

    }

    interface Storable {

    }

    %% Inheritance
    Person <|-- Student : extends
    Person <|-- Faculty : extends

    %% Associations / aggregations / composition
    Main --> Department
    Department "1" o--> Faculty
    Department "1" o--> Student
    Faculty "1" o--> Module
    Faculty "1" o--> Student
    Student "1" o--> Program
    Program "1" o--> Module
    Module "1" *--> Grade
```

## Relationship summary

| From | Relationship | To | Multiplicity (at source) |
|---|---|---|---|
| Student | Inheritance (extends) | Person | – |
| Faculty | Inheritance (extends) | Person | – |
| Main | Association (uses) | Department | – |
| Department | Aggregation | Faculty | 1 |
| Department | Aggregation | Student | 1 |
| Faculty | Aggregation | Module | 1 |
| Faculty | Aggregation | Student | 1 |
| Student | Aggregation | Program | 1 |
| Program | Aggregation | Module | 1 |
| Module | Composition | Grade | 1 |
