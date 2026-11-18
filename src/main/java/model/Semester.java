package model;

/**
 * A semester within an academic year.
 *
 * <p>Each year of study splits into two semesters (some postgraduate programmes have
 * three semesters), and the semester is the unit at which grades are awarded and
 * quality credit averages are calculated (Handbook 2.5.1 and 3.3.1.1).
 *
 * <p>{@link #SUMMER} is declared to match the class diagram but is not implemented:
 * no rule in this version distinguishes a summer semester from {@link #AUTUMN} or
 * {@link #SPRING}. See Assumptions.md for why it is kept and when it has to be handled.
 */
public enum Semester {
    AUTUMN,
    SPRING,
    SUMMER
}
