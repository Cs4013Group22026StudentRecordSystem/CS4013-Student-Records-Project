package model;

/**
 * A semester within an academic year.
 *
 * <p>Each year of study splits into two semesters (Some PostGraduate programme have three semesters), 
 * and the semester is the unit at which grades are awarded and quality credit averages are calculated
 * (Handbook 2.5.1 and 3.3.1.1).
 */
public enum Semester {
    AUTUMN,
    SPRING,
    SUMMER
}
