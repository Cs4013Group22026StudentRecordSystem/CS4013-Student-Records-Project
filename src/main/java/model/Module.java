package model;
//Maybe we should add a name


/**
 * Represents a module
 */
public class Module {
    private final String code;
    private int credits;
    private int semester;
    private Programme programme;    

    /**
     * Creates a module.
     *
     * @param code module code
     * @param credits module credits
     * @param semester module semester
     * @param programme module programme
     */
    public Module(String code, int credits, int semester, Programme programme) {
        this.code = code;
        this.credits = credits;
        this.semester = semester;
        this.programme = programme;
    }

    /**
     * Returns the module code.
     *
     * @return module code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the module credits.
     *
     * @return module credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Returns the module semester.
     *
     * @return module semester
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Returns the module programme.
     *
     * @return module programme
     */
    public Programme getProgramme() {
        return programme;
    }
}
