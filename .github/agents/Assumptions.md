Assumptions (to be completed from the Handbook of Academic Regulations 2026/27, Chapters 2 and 3)

The values in square brackets are placeholders. Fill them in from the Handbook and delete any assumption you do not use.

Grading

Grade scale and quality point values (QPV) for A1 to NG are taken from Table 2.1:
  A1 4.00, A2 3.60, B1 3.20, B2 3.00, B3 2.80, C1 2.60, C2 2.40, C3 2.00,
  D1 1.60, F 0.00, NG 0.00. P and N have no QPV (Table 2.1 shows a dash).
Pass grade: C3, the 40% pass line (Handbook 2.5.3); D1 covers 35% to below 40%.
Deficient grades: F, NG and N, following Handbook 2.5.4 (which also lists I, I2 and
  M; those administrative grades are not modelled).
QCA is calculated as the sum of (QPV x module credits) divided by the attempted
  credits of modules that carry a QPV (Handbook 3.3.1.2). P and N are non-quality
  hours and are excluded from both the numerator and the denominator rather than
  counted as 0.00, which is why Grade stores its QPV as a nullable Double.
D1 awards credits although it is a fail (Table 2.1), so credit counting uses
  Grade.awardsCredits() while a decision on whether the module is cleared uses
  Grade.isPass(). Handbook 2.6.1(b) needs the former, repeat decisions need the latter.
Administrative grades EX, R, G, I, I2, M, RE, X, Y and W (Table 2.2) are not
  implemented: they describe pending or repeat states that need appeal, recheck or
  re-registration workflows which are out of scope for this version.

Progression

Undergraduate rule: [QCA threshold, e.g. cumulative QCA of 2.00, with full credits awarded to date - confirm in Handbook]
Taught postgraduate rule: [threshold from Handbook]
Research postgraduate rule: not implemented - research programmes are out of scope (see Design assumptions)
Meaning of each status (PROGRESS, COMPENSATED_PASS, REPEAT_MODULE, REPEAT_SEMESTER, REPEAT_YEAR, TERMINATE_ENROLMENT): [define for your rules]
Note: TERMINATE_ENROLMENT is required even though the class diagram omitted it -
  Handbook 3.3.5.2(d), 3.3.6.2(d) and 3.3.7.2(d) each list "have their enrolment
  terminated" as an outcome, which no other Decision constant can express.

Compensation

Compulsory modules cannot be compensated (see Design assumptions for where the
  compulsory flag is stored).
Qualifying grade (lowest failing grade that may be compensated): [from Handbook, or team assumption]
Required year QCA for compensation: [from Handbook, or team assumption]
If UL's Handbook has no formal compensation rule, state this and say that StandardCompensationRule is a team assumption.

Awards

Award bands for AwardClassification: [cumulative QCA ranges from Handbook]

Design assumptions

A student has one active registration at a time.
A module can belong to more than one programme (programme_modules.csv).
Compulsory status is stored on the association (ProgrammeModule.mandatory), not on
  the module: a module shared by several programmes can be compulsory on one and an
  elective on another, so the flag only means something once module and programme are
  joined. This replaces the earlier wording "core is stored per module".
modules.csv currently carries a flat semester number (1 to 7) instead of a year and a
  semester. Until that file is split into modules.csv and programme_modules.csv, the
  conversion rule is: yearOfStudy = (n + 1) / 2 and odd n is AUTUMN, even n is SPRING,
  so semester 3 becomes year 2 AUTUMN. This is a team assumption about how the sample
  data was numbered, not a Handbook rule.
Module.title is required by the model but modules.csv has no title column yet; the
  title is filled in when the module data is migrated. No code reads modules.csv, so
  nothing is broken in the meantime.
Faculty responsibility for a module (Module.faculty) is optional because no
  faculty.csv exists yet; Faculty.hasFaculty reports whether it has been recorded.
academicYear is the year the academic year starts (for example 2026 for 2026/27).
Semesters are AUTUMN and SPRING. The diagram also listed SUMMER, but no college
  calendar or module in the sample data uses a summer semester, so it is omitted
  rather than modelled as an unused constant.
Programme duration (Programme.durationYears) is derived from the programme level
  instead of stored in programmes.csv: 4 years for UNDERGRADUATE and 1 year for
  POSTGRADUATE_TAUGHT. Chapters 2 and 3 of the Handbook set grading, progression
  and award rules but say nothing about programme length, so these figures are a
  team assumption, not a regulation. Read from Programme.defaultDuration so the
  single place to change them is the model, and revisit if a real column is added
  to programmes.csv.
Research programmes are out of scope: ProgrammeLevel has no research constant and
  programmes.csv contains none, so the research award rule (awarded without
  classification, Handbook 2.8.1) is not implemented.
Faculty can submit results only for modules they teach.
Link-in modules are not implemented in this version.
Passwords and login are not implemented; the CLI asks for the role and ID.

Assign core responsibilities to **Student**, **Faculty**, and **Department**
| Core operation | Suggested role | Reason |
|---|---|---|
| Add student(s) | **Department** | Maintains student records |
| Add programme | **Department** | Manages programme information |
| Add module | **Department** | Maintains the module catalogue |
| Register student on a programme | **Department** | Manages programme enrolment |
| Record module results | **Faculty** | Faculty submit results for their modules |
| Calculate semester QCA | **Department** | Department can oversee semester QCA |
| Calculate overall QCA | **Department** | A programme-level or cumulative academic calculation |
| Generate transcript | **Student** (own transcript); **Faculty/Department** (authorized lookup) | Matches the brief’s transcript access requirements |
| Identify progression status | **Department** | A progression decision is typically made at programme level |
