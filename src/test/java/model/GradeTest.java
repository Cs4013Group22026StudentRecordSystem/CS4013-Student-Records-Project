package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks the grade semantics against Table 2.1 of the Handbook, with particular
 * attention to D1, the only grade whose credit and pass answers differ.
 */
class GradeTest {

    @Test
    @DisplayName("quality point values match Table 2.1")
    void qualityPointValuesMatchHandbook() {
        assertEquals(4.00, Grade.A1.getQpv());
        assertEquals(3.60, Grade.A2.getQpv());
        assertEquals(3.20, Grade.B1.getQpv());
        assertEquals(3.00, Grade.B2.getQpv());
        assertEquals(2.80, Grade.B3.getQpv());
        assertEquals(2.60, Grade.C1.getQpv());
        assertEquals(2.40, Grade.C2.getQpv());
        assertEquals(2.00, Grade.C3.getQpv());
        assertEquals(1.60, Grade.D1.getQpv());
        assertEquals(0.00, Grade.F.getQpv());
        assertEquals(0.00, Grade.NG.getQpv());
    }

    @Test
    @DisplayName("pass/fail modules carry no quality points rather than zero")
    void passFailGradesHaveNoQpv() {
        assertNull(Grade.P.getQpv());
        assertNull(Grade.N.getQpv());
        assertFalse(Grade.P.hasQpv());
        assertFalse(Grade.N.hasQpv());
        assertTrue(Grade.F.hasQpv());
    }

    @Test
    @DisplayName("D1 awards credits but is not a pass")
    void d1IsTheException() {
        assertTrue(Grade.D1.awardsCredits(), "D1 awards credits per Table 2.1");
        assertFalse(Grade.D1.isPass(), "D1 is a compensating fail and needs a decision");
    }

    @Test
    @DisplayName("every other grade agrees on credits and passing, except the failures")
    void creditsAndPassAgreeExceptForD1() {
        for (Grade grade : Grade.values()) {
            if (grade == Grade.D1) {
                continue;
            }
            boolean expected = grade != Grade.F && grade != Grade.NG && grade != Grade.N;
            assertEquals(expected, grade.awardsCredits(), grade + " credit award");
            assertEquals(expected, grade.isPass(), grade + " pass");
        }
    }

    @Test
    @DisplayName("deficient grades exclude D1")
    void deficientGradesFollowSection254() {
        assertTrue(Grade.F.isDeficient());
        assertTrue(Grade.NG.isDeficient());
        assertTrue(Grade.N.isDeficient());
        assertFalse(Grade.D1.isDeficient(), "2.5.4 lists F, NG, I, I2, M and N only");
        assertTrue(Grade.D1.isCompensatingFail());
        assertFalse(Grade.F.isCompensatingFail());
    }

    @Test
    @DisplayName("annual repeat grades are capped at C3")
    void capAtC3LowersButNeverRaises() {
        assertEquals(Grade.C3, Grade.A1.cappedAtC3());
        assertEquals(Grade.C3, Grade.C1.cappedAtC3());
        assertEquals(Grade.C3, Grade.C3.cappedAtC3());
        assertEquals(Grade.D1, Grade.D1.cappedAtC3());
        assertEquals(Grade.F, Grade.F.cappedAtC3());
        assertEquals(Grade.P, Grade.P.cappedAtC3());
        assertEquals(Grade.N, Grade.N.cappedAtC3());
    }

    @Test
    @DisplayName("the higher of original and repeat grade stands")
    void betterOfPicksHigherQpv() {
        assertEquals(Grade.B1, Grade.C3.betterOf(Grade.B1));
        assertEquals(Grade.B1, Grade.B1.betterOf(Grade.C3));
        assertEquals(Grade.C3, Grade.C3.betterOf(Grade.F));
        assertEquals(Grade.NG, Grade.NG.betterOf(null));
        assertEquals(Grade.P, Grade.P.betterOf(Grade.N));
    }
}
