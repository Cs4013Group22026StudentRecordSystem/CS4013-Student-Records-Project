package model;

/**
 * The honours classification of a degree, and the cumulative QCA it requires.
 *
 * <p>The bands and their thresholds are taken from Handbook Table 2.6.2 for
 * bachelor's degrees and Table 2.7.5 for taught master's degrees. The two tables
 * are identical, so one enumeration serves both levels.
 *
 * <p>Two things are deliberately not part of this type:
 *
 * <ul>
 *   <li><b>Eligibility.</b> A band says which classification a cumulative QCA
 *       falls in. It does not say that the student is entitled to an award at
 *       all: Handbook 2.6.1(b) and 2.7.1(c) also require "full credits in all
 *       prescribed modules", which no QCA threshold can express. That check
 *       belongs to the code that asks for a band, not to the band itself.</li>
 *   <li><b>"No classification".</b> Research awards carry no classification
 *       (Handbook 2.8.1), and a student below 2.00 receives no honours award.
 *       Neither is a classification, so neither is a constant here; a constant
 *       called {@code NONE} would have to mean both and could not be compared
 *       against a threshold.</li>
 * </ul>
 */
public enum AwardClassification {
    FIRST_CLASS_HONOURS(3.30, "First class honours"),
    SECOND_CLASS_HONOURS_1(2.90, "Second class honours grade 1 (2.1)"),
    SECOND_CLASS_HONOURS_2(2.50, "Second class honours grade 2 (2.2)"),
    THIRD_CLASS_HONOURS(2.00, "Third class honours");

    private final double minimumQca;
    private final String description;

    AwardClassification(double minimumQca, String description) {
        this.minimumQca = minimumQca;
        this.description = description;
    }

    /**
     * Returns the lowest cumulative QCA that earns this classification.
     *
     * @return the threshold from Table 2.6.2, for example 2.90 for a 2.1
     */
    public double getMinimumQca() {
        return minimumQca;
    }

    /**
     * Returns the Handbook description of this classification.
     *
     * @return the award descriptor
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the highest classification a cumulative QCA reaches.
     *
     * <p>The comparison runs against {@link #getMinimumQca()} rather than the
     * declaration order, so reordering the constants cannot change the result.
     *
     * <p>This answers the banding question only. Turning the returned value into
     * an award still requires the credit condition in Handbook 2.6.1(b).
     *
     * @param cumulativeQca the student's cumulative QCA
     * @return the classification whose threshold the QCA meets
     * @throws IllegalArgumentException when the QCA does not reach the third
     *     class honours threshold, because such a student is not awarded an
     *     honours degree and so has no classification to return
     */
    public static AwardClassification classify(double cumulativeQca) {
        AwardClassification reached = null;
        for (AwardClassification band : values()) {
            if (cumulativeQca >= band.minimumQca
                    && (reached == null || band.minimumQca > reached.minimumQca)) {
                reached = band;
            }
        }
        if (reached == null) {
            throw new IllegalArgumentException(
                    "cumulative QCA " + cumulativeQca + " does not reach "
                            + THIRD_CLASS_HONOURS.minimumQca + ", so no honours award applies");
        }
        return reached;
    }
}
