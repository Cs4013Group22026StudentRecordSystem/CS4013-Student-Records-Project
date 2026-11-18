package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks the award bands against Handbook Tables 2.6.2 and 2.7.5.
 */
class AwardClassificationTest {

    @Test
    @DisplayName("thresholds match Table 2.6.2")
    void thresholdsMatchHandbook() {
        assertEquals(3.30, AwardClassification.FIRST_CLASS_HONOURS.getMinimumQca());
        assertEquals(2.90, AwardClassification.SECOND_CLASS_HONOURS_1.getMinimumQca());
        assertEquals(2.50, AwardClassification.SECOND_CLASS_HONOURS_2.getMinimumQca());
        assertEquals(2.00, AwardClassification.THIRD_CLASS_HONOURS.getMinimumQca());
    }

    @Test
    @DisplayName("a QCA on a threshold reaches that classification")
    void thresholdIsInclusive() {
        assertEquals(AwardClassification.FIRST_CLASS_HONOURS,
                AwardClassification.classify(3.30));
        assertEquals(AwardClassification.SECOND_CLASS_HONOURS_1,
                AwardClassification.classify(2.90));
        assertEquals(AwardClassification.SECOND_CLASS_HONOURS_2,
                AwardClassification.classify(2.50));
        assertEquals(AwardClassification.THIRD_CLASS_HONOURS,
                AwardClassification.classify(2.00));
    }

    @Test
    @DisplayName("a QCA between two thresholds takes the lower band")
    void betweenThresholdsTakesLowerBand() {
        assertEquals(AwardClassification.THIRD_CLASS_HONOURS,
                AwardClassification.classify(2.49));
        assertEquals(AwardClassification.SECOND_CLASS_HONOURS_2,
                AwardClassification.classify(2.89));
        assertEquals(AwardClassification.SECOND_CLASS_HONOURS_1,
                AwardClassification.classify(3.29));
        assertEquals(AwardClassification.FIRST_CLASS_HONOURS,
                AwardClassification.classify(4.00));
    }

    @Test
    @DisplayName("a QCA below 2.00 has no classification")
    void belowThirdClassIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> AwardClassification.classify(1.99));
    }
}
