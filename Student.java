import java.util.ArrayList;

/**
 * Represents a student, their programme, and their module grades.
 */
public class Student extends Person {
    private static final double PROGRESSION_QCA_THRESHOLD = 2.00;

    private Programme programme;
    private String progressionStatus;
    private int currentYear;
    private int currentSemester;

    public Student(String name, String studentId) {
        super(name, studentId);
        this.progressionStatus = "NOT_ASSESSED";
        this.currentYear = 1;
        this.currentSemester = 1;
    }

    /**
     * Gets the programme on which this student is registered.
     *
     * @return the registered programme, or {@code null} if not registered
     */
    public Programme getProgramme() {
        return programme;
    }

    /**
     * Registers this student on a private copy of a programme.
     *
     * @param programme the programme
     * @throws IllegalStateException if the student has a recorded grade and their
     *                                programme would change
     */
    public void registerOnProgram(Programme programme) {
        if (this.programme != null && programme != null
                && this.programme.getCode().equals(programme.getCode())) {
            return;
        }
        if (hasRecordedGrades()) {
            throw new IllegalStateException(
                    "Cannot change a student's programme after recording grades.");
        }
        this.programme = programme == null ? null : programme.copy();
    }

    /**
     * Gets this student's module copy by its module code.
     *
     * @param moduleCode the module code
     * @return the student's module copy, or {@code null} if not registered for it
     */
    public Module getRegisteredModule(String moduleCode) {
        if (programme == null || moduleCode == null) {
            return null;
        }
        for (Module module : programme.getModules()) {
            if (moduleCode.equals(module.getCode())) {
                return module;
            }
        }
        return null;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        if (currentYear < 1) {
            throw new IllegalArgumentException("Current year must be positive.");
        }
        this.currentYear = currentYear;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        if (currentSemester < 1) {
            throw new IllegalArgumentException("Current semester must be positive.");
        }
        this.currentSemester = currentSemester;
    }

    public String getProgressionStatus() {
        return progressionStatus;
    }

    public void setProgressionStatus(String progressionStatus) {
        if (!"NOT_ASSESSED".equals(progressionStatus)
                && !"PROGRESS".equals(progressionStatus)
                && !"REPEAT".equals(progressionStatus)) {
            throw new IllegalArgumentException(
                    "Unsupported progression status: " + progressionStatus);
        }
        this.progressionStatus = progressionStatus;
    }

    /**
     * Gets the modules assigned to a course semester.
     *
     * @param semester the globally numbered course semester
     * @return this student's module copies assigned to the semester
     */
    public ArrayList<Module> getModulesIn(int semester) {
        ArrayList<Module> semesterModules = new ArrayList<>();
        if (programme == null) {
            return semesterModules;
        }
        for (Module module : programme.getModules()) {
            if (programme.getModuleSemester(module.getCode()) == semester) {
                semesterModules.add(module);
            }
        }
        return semesterModules;
    }

    /**
     * Records or replaces the grade on this student's module copy.
     *
     * @param grade the grade awarded
     * @param module the catalogue module
     */
    public void receiveGrade(Grade grade, Module module) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }
        if (module == null || module.getCode() == null) {
            throw new IllegalArgumentException("Module and module code are required.");
        }
        Module studentModule = getRegisteredModule(module.getCode());
        if (studentModule == null) {
            throw new IllegalArgumentException(
                    "Student is not registered for module: " + module.getCode());
        }
        studentModule.setGrade(grade);
    }

    /**
     * Calculates the credit-weighted QCA for the student's current semester.
     *
     * @return the current semester QCA, or {@code 0.0} if there are no recorded
     *         quality grades in that semester
     */
    public double calculateSemesterQCA() {
        return calculateQCA(currentSemester);
    }

    /**
     * Calculates cumulative QCA over all recorded quality grades.
     *
     * @return the cumulative QCA, or {@code 0.0} when none are recorded
     */
    public double calculateOverallQCA() {
        return calculateQCA(null);
    }

    /**
     * Determines progression from the cumulative QCA.
     *
     * @return {@code "PROGRESS"} or {@code "REPEAT"}
     */
    public String identifyProgressionStatus() {
        if (calculateOverallQCA() >= PROGRESSION_QCA_THRESHOLD) {
            progressionStatus = "PROGRESS";
        } else {
            progressionStatus = "REPEAT";
        }
        return progressionStatus;
    }

    /**
     * Advances the student to the next semester when progression is permitted.
     *
     * @throws IllegalStateException if the student is not currently eligible
     */
    public void progress() {
        if (!"PROGRESS".equals(progressionStatus)) {
            throw new IllegalStateException("Student cannot progress in their current state.");
        }
        currentSemester++;
    }

    @Override
    public String generateTranscript() {
        StringBuilder transcript = new StringBuilder();
        transcript.append("Student: ").append(getName())
                .append(" (").append(getPersonId()).append(")")
                .append(System.lineSeparator());

        if (programme != null) {
            for (Module module : programme.getModules()) {
                Grade grade = module.getGrade();
                if (grade == null) {
                    continue;
                }
                transcript.append(module.getCode())
                        .append(" - ").append(module.getName())
                        .append(": ").append(grade)
                        .append(" (semester ")
                        .append(programme.getModuleSemester(module.getCode()))
                        .append(")")
                        .append(System.lineSeparator());
            }
        }

        transcript.append("Semester QCA: ").append(calculateSemesterQCA())
                .append(System.lineSeparator())
                .append("Overall QCA: ").append(calculateOverallQCA());
        return transcript.toString();
    }

    private double calculateQCA(Integer semester) {
        double qualityPoints = 0.0;
        int credits = 0;
        if (programme == null) {
            return 0.0;
        }
        for (Module module : programme.getModules()) {
            Grade grade = module.getGrade();
            if (grade == null || (semester != null
                    && programme.getModuleSemester(module.getCode()) != semester)
                    || !isQualityGraded(grade)) {
                continue;
            }
            int credit = module.getCreditValue();
            qualityPoints += getGradeQCA(grade) * credit;
            credits += credit;
        }
        return credits == 0 ? 0.0 : qualityPoints / credits;
    }

    private boolean hasRecordedGrades() {
        if (programme == null) {
            return false;
        }
        for (Module module : programme.getModules()) {
            if (module.getGrade() != null) {
                return true;
            }
        }
        return false;
    }

    private boolean isQualityGraded(Grade grade) {
        return grade != Grade.P && grade != Grade.N;
    }

    private double getGradeQCA(Grade grade) {
        switch (grade) {
            case A1:
                return 4.00;
            case A2:
                return 3.60;
            case B1:
                return 3.20;
            case B2:
                return 3.00;
            case B3:
                return 2.80;
            case C1:
                return 2.60;
            case C2:
                return 2.40;
            case C3:
                return 2.00;
            case D1:
                return 1.60;
            default:
                return 0.00;
        }
    }
}
