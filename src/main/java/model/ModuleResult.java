package model;

public class ModuleResult {
    private int resultId;
    private String studentId;  
    private String moduleCode;
    private int attempt;
    private String grade;


    /**
     * Creates a module result.
     *
     * @param resultId module result id
     * @param studentId student id
     * @param moduleCode module code
     * @param attempt attempt number
     * @param grade grade achieved
     */
    public ModuleResult(int resultId, String studentId, String moduleCode, int attempt, String grade) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.moduleCode = moduleCode;
        this.attempt = attempt;
        this.grade = grade;
    }

    /**
     * Returns the module result id.
     *
     * @return module result id
     */
    public int getResultId() {
        return resultId;  
    }

    /**
     * Returns the student id.
     *
     * @return student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Returns the module code.
     *
     * @return module code
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Returns the attempt number.
     *
     * @return attempt number
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * Returns the grade achieved.
     *
     * @return grade achieved
     */
    public String getGrade() {
        return grade;
    }


}
