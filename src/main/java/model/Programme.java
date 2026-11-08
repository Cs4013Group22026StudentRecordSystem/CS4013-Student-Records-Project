package model;

/**
 * Represents a programme of study.
 */
public class Programme {
    private final String code;
    private final String name;
    private final ProgrammeLevel level;

    /**
     * Creates a programme.
     *
     * @param code programme code
     * @param name programme name
     * @param level programme level
     */
    public Programme(String code, String name, ProgrammeLevel level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }

    /**
     * Returns the programme code.
     *
     * @return programme code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the programme name.
     *
     * @return programme name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the programme level.
     *
     * @return programme level
     */
    public ProgrammeLevel getLevel() {
        return level;
    }
}
