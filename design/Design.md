# Design

## List of classes

- **Main** (to run the program)
- **Department** (holds exam boards, calculates QCA...)
- **Student** (can view the transcript)
- **Faculty** (can look up transcript and upload results to the module's result CSV)
- **\<\<abstract\>\> Person** (Student and Faculty inherit from Person)
- **Program**
- **Module**
- **\<\<enumeration\>\> Grade**

---

- **CLI_class**
- **CSV_handler** (to read, write and update CSV)
- **CLI interface UserInterface** (CLI_class implements Presentable)
- **CSV interface Storable** (CSV_handler implements Storable for result persistence)

## Core requirements

- Add student(s) — **R1**
- Add program — **R2**
- Add module — **R3**

---

- Register student on program — **R4**

---

- Record module results — **R5**

---

- Calculate semester QCA — **R6**
- Calculate overall QCA — **R7**

---

- Generate Transcript — **R8**

---

- Identify progression status — **R9**

## Core requirements coverage map

| Requirement | Responsible class(es) |
| --- | --- |
| R1: Add student(s) | Department |
| R2: Add program | Department |
| R3: Add module | Program (to this program), Department (just add standalone module) |
| R4: Register student on program | Student (contains implementation), Department (invokes method) |
| R5: Record module results | Faculty (invokes method and passes grade {A1, A2, B1, B2, B3 ...} as parameter), Student (has method implementation) |
| R6: Calculate semester QCA | Student (has implementation), Department (invokes the method) |
| R7: Calculate overall QCA | Student (has implementation), Department (invokes the method) |
| R8: Generate Transcript | Student (can generate own transcript only), Faculty (can generate transcript for their module) |
| R9: Identify progression status | Student (contains implementation), Department (invokes method) |

## Additional requirements needed by design

- Add faculty for the specific module — **A1**
- Progress student to next semester — **A2**

## Additional requirements coverage map

| Requirement | Responsible class(es) |
| --- | --- |
| A1: Add faculty for the specific module | Department |
| A2: Progress student to next semester | Department (if the student’s progression status is “progress”), Student (contains implementation) |

## Assumptions

- Professor (Faculty) can teach only one module at a time. Faculty can choose what module to teach only if they currently do not teach any student (when Faculty teaches at least one student, they cannot change the module);
- Faculty has ArrayList\<Student\> because they need to grade every individual student (Professor cannot put a single grade for the whole module (class), each Student receives their grade)
- There is a differentiation between Object and Role. We have 3 roles:
  - DEPARTMENT
  - FACULTY
  - STUDENT

  And we have objects: student1, student2, faculty1, faculty2 ...

- The student class should have lots of methods (calculate QCA), but STUDENT role has limited features (only view their own transcript)
