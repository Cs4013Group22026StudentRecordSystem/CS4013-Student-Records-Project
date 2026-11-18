package model;

import java.util.Objects;

/**
 * One semester of one academic year, for example {@code 2025/26 AUTUMN}.
 *
 * <p>Academic years and semesters are modelled separately so that a result can
 * say both which year it belongs to and which semester within it, which the
 * cumulative QCA calculation needs (Handbook 3.3.1.1).
 *
 * <p>Immutable value object: two periods are equal when both the year and the
 * semester match.
 */
public final class AcademicPeriod {
    private final String academicYear;
    private final Semester semester;

    /**
     * Creates an academic period.
     *
     * @param academicYear academic year label, conventionally {@code 2025/26}
     * @param semester semester within that academic year
     */
    public AcademicPeriod(String academicYear, Semester semester) {
        if (academicYear == null || academicYear.isBlank()) {
            throw new IllegalArgumentException("academicYear is required");
        }
        this.academicYear = academicYear.trim();
        this.semester = Objects.requireNonNull(semester, "semester is required");
    }

    /**
     * Returns the academic year label.
     *
     * @return the academic year, conventionally {@code 2025/26}
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * Returns the semester within the academic year.
     *
     * @return the semester
     */
    public Semester getSemester() {
        return semester;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AcademicPeriod period)) {
            return false;
        }
        return academicYear.equals(period.academicYear) && semester == period.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(academicYear, semester);
    }

    @Override
    public String toString() {
        return academicYear + " " + semester;
    }
}
