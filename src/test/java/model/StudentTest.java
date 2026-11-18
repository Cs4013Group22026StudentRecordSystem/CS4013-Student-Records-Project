package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks how a student's year of study is created and advanced.
 *
 * <p>The year is the only mutable state in the model, so the tests cover the
 * two places that may change it: the constructor, which validates the value it
 * is given, and {@link Student#advanceYear()}, which refuses to move past the
 * last year of the programme.
 */
class StudentTest {

    private final Programme undergraduate = new Programme("LM121", "Computer Science",
            ProgrammeLevel.UNDERGRADUATE);
    private final Programme taughtMasters = new Programme("LK274", "Artificial Intelligence",
            ProgrammeLevel.POSTGRADUATE_TAUGHT);

    @Test
    @DisplayName("a student created without a year starts in first year")
    void defaultsToFirstYear() {
        Student student = new Student("25321633", "Alice Chen", undergraduate);
        assertEquals(1, student.getCurrentYear());
    }

    @Test
    @DisplayName("the year of study given to the constructor is kept")
    void keepsGivenYear() {
        Student student = new Student("25321691", "Chloe Wilson", undergraduate, 3);
        assertEquals(3, student.getCurrentYear());
    }

    @Test
    @DisplayName("a year below one is rejected")
    void rejectsYearBelowOne() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student("25321633", "Alice Chen", undergraduate, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Student("25321633", "Alice Chen", undergraduate, -1));
    }

    @Test
    @DisplayName("a year beyond the length of the programme is rejected")
    void rejectsYearBeyondProgrammeLength() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student("25321633", "Alice Chen", undergraduate, 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Student("25327401", "Priya Nair", taughtMasters, 2));
    }

    @Test
    @DisplayName("advancing a year moves a student towards the final year")
    void advanceYearIncrements() {
        Student student = new Student("25321691", "Chloe Wilson", undergraduate, 2);
        student.advanceYear();
        assertEquals(3, student.getCurrentYear());
    }

    @Test
    @DisplayName("advancing past the final year is rejected")
    void advanceYearStopsAtFinalYear() {
        Student undergraduateFinal = new Student("25321701", "Fionn Kelly", undergraduate, 4);
        assertThrows(IllegalStateException.class, undergraduateFinal::advanceYear);
        assertEquals(4, undergraduateFinal.getCurrentYear());

        Student mastersFinal = new Student("25327401", "Priya Nair", taughtMasters, 1);
        assertThrows(IllegalStateException.class, mastersFinal::advanceYear);
        assertEquals(1, mastersFinal.getCurrentYear());
    }

    @Test
    @DisplayName("a student identifier is the person identifier")
    void studentIdIsPersonId() {
        Student student = new Student("25321633", "Alice Chen", "alice@example.com", undergraduate, 1);
        assertEquals("25321633", student.getStudentId());
        assertEquals(student.getStudentId(), student.getPersonId());
    }
}
