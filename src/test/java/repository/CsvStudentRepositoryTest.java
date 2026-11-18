package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks that the shipped {@code students.csv} can be loaded and that the
 * {@code current_year} column reaches the model.
 *
 * <p>This reads the real data directory rather than a fixture on purpose: the
 * column count and the year values are agreed in the CSV, so a change that
 * updates only one side would otherwise only show up when the application runs.
 */
class CsvStudentRepositoryTest {

    private static IStudentRepository students;

    @BeforeAll
    static void loadShippedData() throws IOException {
        IProgrammeRepository programmes = new CsvProgrammeRepository("data");
        students = new CsvStudentRepository("data", programmes);
    }

    @Test
    @DisplayName("every student row is loaded")
    void loadsAllStudents() {
        assertEquals(60, students.findAll().size());
    }

    @Test
    @DisplayName("the current_year column is read into the student")
    void readsCurrentYear() {
        Student firstYear = students.findById("25321633");
        assertNotNull(firstYear);
        assertEquals(1, firstYear.getCurrentYear());

        Student finalYear = students.findById("25321666");
        assertNotNull(finalYear);
        assertEquals(4, finalYear.getCurrentYear());
    }

    @Test
    @DisplayName("a taught masters student cannot be beyond the one-year programme")
    void mastersStudentIsInFirstYear() {
        Student masters = students.findById("25321669");
        assertNotNull(masters);
        assertEquals(1, masters.getCurrentYear());
    }
}
