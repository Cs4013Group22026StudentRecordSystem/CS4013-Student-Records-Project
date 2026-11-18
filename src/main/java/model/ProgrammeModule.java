package model;

import java.util.Objects;

/**
 * Links a module to a programme: the association object for the many-to-many
 * relationship between the two.
 *
 * <p>A module can be taught on several programmes, and a programme prescribes
 * many modules, so the relationship cannot live inside either class without one
 * of them holding a second copy. It lives here instead, and it carries the
 * details that only make sense once the two are joined: the year of study, the
 * semester and whether the module is compulsory for that programme.
 *
 * <p>Whether a module is compulsory depends on the programme, not on the module.
 * A shared module can be mandatory on one programme and an elective on another,
 * which is why this flag is stored on the association rather than on
 * {@link Module}.
 */
public class ProgrammeModule {
    private final Programme programme;
    private final Module module;
    private final int yearOfStudy;
    private final Semester semester;
    private final boolean mandatory;

    /**
     * Creates a programme-module association.
     *
     * @param programme programme that prescribes the module
     * @param module module prescribed by the programme
     * @param yearOfStudy year of the programme in which the module is taken,
     *     counted from 1
     * @param semester semester in which the module runs
     * @param mandatory whether the module is compulsory for this programme
     */
    public ProgrammeModule(Programme programme, Module module, int yearOfStudy,
            Semester semester, boolean mandatory) {
        if (programme == null) {
            throw new IllegalArgumentException("programme is required");
        }
        if (module == null) {
            throw new IllegalArgumentException("module is required");
        }
        if (semester == null) {
            throw new IllegalArgumentException("semester is required");
        }
        if (yearOfStudy < 1) {
            throw new IllegalArgumentException("yearOfStudy counts from 1");
        }
        this.programme = programme;
        this.module = module;
        this.yearOfStudy = yearOfStudy;
        this.semester = semester;
        this.mandatory = mandatory;
    }

    /**
     * Returns the programme that prescribes this module.
     *
     * @return the programme
     */
    public Programme getProgramme() {
        return programme;
    }

    /**
     * Returns the module prescribed by the programme.
     *
     * @return the module
     */
    public Module getModule() {
        return module;
    }

    /**
     * Returns the year of study in which this module is taken.
     *
     * @return year of study, counted from 1
     */
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    /**
     * Returns the semester in which this module runs.
     *
     * @return the semester
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Reports whether this module is compulsory for the programme.
     *
     * <p>Compulsory modules matter beyond credit counting: the award condition
     * in Handbook 2.6.1(b) requires full credits in all prescribed modules, so
     * the progression rules need to tell a compulsory module from an optional
     * one.
     *
     * @return {@code true} when the module is compulsory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Compares associations by programme and module.
     *
     * <p>A module belongs to a given programme once, so the pair identifies the
     * association. Year and semester are attributes of that membership rather
     * than part of its identity: moving a module to another year changes the
     * association, it does not create a second one.
     *
     * @param other object to compare with
     * @return {@code true} when both associations join the same programme and
     *     module
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProgrammeModule)) {
            return false;
        }
        ProgrammeModule that = (ProgrammeModule) other;
        return programme.equals(that.programme) && module.equals(that.module);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash of the programme and module
     */
    @Override
    public int hashCode() {
        return Objects.hash(programme, module);
    }

    /**
     * Returns a readable description of this association.
     *
     * @return the programme, module, position in the programme and whether it is
     *     compulsory
     */
    @Override
    public String toString() {
        return programme.getProgrammeCode() + " / " + module.getModuleCode()
                + " (year " + yearOfStudy + ", " + semester
                + (mandatory ? ", mandatory" : ", optional") + ")";
    }
}
