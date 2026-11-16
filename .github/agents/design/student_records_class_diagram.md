# Student Records System - UML Class Diagram

```mermaid
classDiagram
    direction TB

    class StudentRecordsMenu {
        -system: StudentRecordsSystem
        +displayMenu() void
        +run() void
        +handleChoice(int) void
    }

    class StudentRecordsSystem {
        -students: List~Student~
        -programmes: List~Programme~
        -modules: List~Module~
        -fileManager: FileStorage
        +addStudent(Student) void
        +addProgramme(Programme) void
        +addModule(Module) void
        +registerStudent(String, String) void
        +recordResult(String, String, String, int, int) void
        +getStudent(String) Student
        +generateTranscript(String) Transcript
        +calculateSemesterQCA(String, int, int) double
        +calculateOverallQCA(String) double
        +checkProgression(String) String
    }

    class Faculty {
        -staffId: String
        -name: String
        -department: String
        +submitResult(String, String, String, int, int) void
        +getModules() List~Module~
    }

    class Student {
        -studentId: String
        -name: String
        -email: String
        -programme: Programme
        -results: List~Result~
        +addResult(Result) void
        +getResults() List~Result~
        +getProgramme() Programme
        +getOverallQCA() double
    }

    class Programme {
        <<abstract>>
        #programmeCode: String
        #name: String
        #durationYears: int
        #level: String
        +getProgrammeCode() String
        +getName() String
        +getModules() List~Module~
        +addModule(Module) void
        +canProgress(double) boolean
    }

    class UndergraduateProgramme {
        +canProgress(double) boolean
    }

    class PostgraduateProgramme {
        +canProgress(double) boolean
    }

    class Module {
        -moduleCode: String
        -title: String
        -credits: int
        -semester: int
        +getModuleCode() String
        +getCredits() int
        +getSemester() int
    }

    class Result {
        -grade: String
        -gradePoints: double
        -academicYear: int
        -semester: int
        +getGrade() String
        +getGradePoints() double
        +getAcademicYear() int
        +getSemester() int
    }

    class Transcript {
        -student: Student
        -results: List~Result~
        -semesterQCA: double
        -overallQCA: double
        +generate() String
        +display() void
    }

    class QCACalculator {
        +calculateSemesterQCA(List~Result~) double
        +calculateOverallQCA(List~Result~) double
    }

    class ProgressionChecker {
        +checkProgression(Student) String
        +applyRepeatRules(Student) void
        +applyCompensationRules(Student) void
    }

    %% Menu and system
    StudentRecordsMenu --> StudentRecordsSystem : uses

    %% Composition (system owns its collections)
    StudentRecordsSystem "1" *-- "0..*" Student
    StudentRecordsSystem "1" *-- "0..*" Programme
    StudentRecordsSystem "1" *-- "0..*" Module

    %% Associations
    Faculty "1" --> "0..*" Programme : teaches
    Student "0..*" --> "1" Programme : registered on
    Programme "1..*" --> "0..*" Module : offers
    Module "1" --> "0..*" Result : has results
    Student "1" --> "0..*" Result : has

    %% Inheritance
    Programme <|-- UndergraduateProgramme
    Programme <|-- PostgraduateProgramme

    %% Dependencies
    StudentRecordsSystem ..> Transcript
    StudentRecordsSystem ..> QCACalculator
    StudentRecordsSystem ..> ProgressionChecker
```

/**
There are few things I spotted about that diagram.
1. Sense one module can be shared by multiple programmes, so the relation of module and programme cannot be like this. -- Done
2. And also there are several modules are shared by postgraduate student and undergraduate student, like CS4416 DATABASE SYSTEMS. --
So I think this abstract class part should be adjust.
And there is also a problem at relation between faculty and student, the faculty not just directly teaching student, he/she response to module, and students are enrolled in a module.
And another problem confused me is the relationship between Module and Result, the Result should have fields type is Module, Student and the relationship should like:

Student "1" *-- "0..*" Result
Module  "1" *-- "0..*" Result
Result --> Student
Result --> Module
*/

Student and faculty may inherite from the class Person