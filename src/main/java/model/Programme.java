package model;

import java.util.Objects;

/**
 * A programme of study that modules can be shared between and that students
 * register on.
 *
 * <p>
 * The programme does not hold its modules. Membership is owned by
 * {@link ProgrammeModule}, the same one-owner-per-association rule applied
 * elsewhere in the model, so a module can be shared by several programmes
 * without either class holding a second copy of the relationship.
 */
public class Programme {
    private final String programmeCode;
    private final String name;
    private final int durationYears;
    private final ProgrammeLevel level;

    /**
     * Creates a programme using the usual duration for its level.
     *
     * <p>
     * The duration is derived rather than read from {@code programmes.csv};
     * see {@link #defaultDuration(ProgrammeLevel)} for the reasoning.
     *
     * @param programmeCode unique programme code
     * @param name          programme name
     * @param level         programme level
     */
    public Programme(String programmeCode, String name, ProgrammeLevel level) {
        this(programmeCode, name, defaultDuration(level), level);
    }

    /**
     * Creates a programme.
     *
     * @param programmeCode unique programme code
     * @param name          programme name
     * @param durationYears normal length of the programme in academic years
     * @param level         programme level
     */
    public Programme(String programmeCode, String name, int durationYears, ProgrammeLevel level) {
        if (programmeCode == null || programmeCode.isBlank()) {
            throw new IllegalArgumentException("programmeCode is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (level == null) {
            throw new IllegalArgumentException("level is required");
        }
        if (durationYears < 1) {
            throw new IllegalArgumentException("durationYears must be at least 1");
        }
        this.programmeCode = programmeCode.trim();
        this.name = name.trim();
        this.durationYears = durationYears;
        this.level = level;
    }

    /**
     * Returns the normal length of a programme at the given level.
     *
     * <p>
     * The Handbook covers grading and progression but not programme length,
     * so these values are a team assumption rather than a regulation: taught
     * masters are one year, undergraduate degrees are four. Recorded in
     * {@code .github/agents/Assumptions.md}.
     *
     * @param level programme level
     * @return the assumed duration in academic years
     */
    public static int defaultDuration(ProgrammeLevel level) {
        if (level == null) {
            throw new IllegalArgumentException("level is required");
        }
        return level == ProgrammeLevel.UNDERGRADUATE ? 4 : 1;
    }

    /**
     * Returns the programme code.
     *
     * @return programme code
     */
    public String getProgrammeCode() {
        return programmeCode;
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
     * Returns the normal length of this programme.
     *
     * @return duration in academic years
     */
    public int getDurationYears() {
        return durationYears;
    }

    /**
     * Returns the programme level.
     *
     * @return programme level
     */
    public ProgrammeLevel getLevel() {
        return level;
    }

    /**
     * Compares programmes by code, which is what identifies one in the data.
     *
     * <p>
     * Value equality matters because {@link ProgrammeModule} holds a
     * {@code Programme}: without it, two instances read from the same CSV row
     * would not be equal and neither would the associations built from them.
     *
     * @param other object to compare with
     * @return {@code true} when both are programmes with the same code
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Programme)) {
            return false;
        }
        return programmeCode.equals(((Programme) other).programmeCode);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash of the programme code
     */
    @Override
    public int hashCode() {
        return Objects.hash(programmeCode);
    }

    /**
     * Returns a readable description of this programme.
     *
     * @return the code, name, level and duration
     */
    @Override
    public String toString() {
        return programmeCode + " " + name + " (" + level + ", " + durationYears + " year"
                + (durationYears == 1 ? "" : "s") + ")";
    }
}
