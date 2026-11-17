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

Core modules (Module.core = true) cannot be compensated.
Qualifying grade (lowest failing grade that may be compensated): [from Handbook, or team assumption]
Required year QCA for compensation: [from Handbook, or team assumption]
If UL's Handbook has no formal compensation rule, state this and say that StandardCompensationRule is a team assumption.

Awards

Award bands for AwardClassification: [cumulative QCA ranges from Handbook]

Design assumptions

A student has one active registration at a time.
A module can belong to more than one programme (programme_modules.csv).
core is stored per module, not per programme.
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