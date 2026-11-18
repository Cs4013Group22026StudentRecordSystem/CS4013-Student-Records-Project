package model;

import java.util.Objects;

/**
 * A unit of study that carries credits and a grade.
 *
 * <p>A module does not know which programmes use it. That membership is owned by
 * {@link ProgrammeModule}, so the same module can be prescribed by several
 * programmes without either class holding a second copy of the relationship.
 * The module also does not record the year or semester in which it is taught,
 * because those depend on the programme rather than on the module itself.
 */
public class Module {
    private final String moduleCode;
    private final String title;
    private final int credits;
    private final Faculty faculty;

    /**
     * Creates a module whose teaching responsibility is not on record.
     *
     * @param moduleCode unique module code
     * @param title module title
     * @param credits credit value of the module
     */
    public Module(String moduleCode, String title, int credits) {
        this(moduleCode, title, credits, null);
    }

    /**
     * Creates a module.
     *
     * @param moduleCode unique module code
     * @param title module title
     * @param credits credit value of the module
     * @param faculty staff member responsible for the module, or {@code null}
     *     when not on record
     */
    public Module(String moduleCode, String title, int credits, Faculty faculty) {
        if (moduleCode == null || moduleCode.isBlank()) {
            throw new IllegalArgumentException("moduleCode is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (credits < 1) {
            throw new IllegalArgumentException("credits must be at least 1");
        }
        this.moduleCode = moduleCode.trim();
        this.title = title.trim();
        this.credits = credits;
        this.faculty = faculty;
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
     * Returns the module title.
     *
     * @return module title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the credit value of this module.
     *
     * <p>Credits weight the quality points in the QCA calculation (Handbook
     * 3.3.1.2, note 1).
     *
     * @return credit value
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Returns the staff member responsible for this module.
     *
     * @return the responsible faculty member, or {@code null} when not on record
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * Reports whether the responsible staff member is known.
     *
     * @return {@code true} when a faculty member is recorded
     */
    public boolean hasFaculty() {
        return faculty != null;
    }

    /**
     * Compares modules by code, which is what identifies one in the data.
     *
     * <p>Results and programme membership both hold a {@code Module}, so
     * identity by code keeps two instances built from the same row equal.
     *
     * @param other object to compare with
     * @return {@code true} when both are modules with the same code
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Module)) {
            return false;
        }
        return moduleCode.equals(((Module) other).moduleCode);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return hash of the module code
     */
    @Override
    public int hashCode() {
        return Objects.hash(moduleCode);
    }

    /**
     * Returns a readable description of this module.
     *
     * @return the code, title and credit value
     */
    @Override
    public String toString() {
        return moduleCode + " " + title + " (" + credits + " credits)";
    }
}

