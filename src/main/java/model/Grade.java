package model;

/**
 * An academic module grade and the quality points it carries.
 *
 * <p>Values and quality point values (QPV) are taken from Table 2.1 of the
 * Handbook. Two properties of that table are easy to conflate and are therefore
 * modelled separately, because a single pass/fail flag cannot express both:
 *
 * <ul>
 *   <li>{@link #awardsCredits()} follows the "Credits Awarded" column. It answers
 *       whether a module is finished for the purpose of a credit count, which is
 *       what the award condition in Handbook 2.6.1(b) ("full credits in all
 *       prescribed modules") requires.</li>
 *   <li>{@link #isPass()} answers whether the module is passed outright, using
 *       the 40% pass line that Table 2.1 puts at C3 (Handbook 2.5.3).</li>
 * </ul>
 *
 * <p>D1 is the only grade for which these disagree: it is a fail, yet it awards
 * credits. Merging the two questions into one flag would silently misclassify
 * D1, either letting a student progress without a compensation decision or
 * forcing a stored grade to be rewritten once a decision is made. Keeping them
 * apart lets {@link Decision#COMPENSATED_PASS} record the outcome while the
 * grade itself stays untouched.
 *
 * <p>The administrative grades in Table 2.2 (EX, R, G, I, I2, M, RE, X, Y, W)
 * are not modelled; see {@code Assumptions.md}.
 */
public enum Grade {
    A1(4.00, "Outstanding performance (first honours)"),
    A2(3.60, "Excellent performance (first honours)"),
    B1(3.20, "Very good performance (honours 2.1)"),
    B2(3.00, "Good performance (honours 2.1)"),
    B3(2.80, "Competent performance (honours 2.2)"),
    C1(2.60, "Satisfactory performance (honours 2.2)"),
    C2(2.40, "Acceptable performance (third honours)"),
    C3(2.00, "Minimally acceptable performance (third honours)"),
    D1(1.60, "Weak performance (compensating fail)"),
    F(0.00, "Fail (no compensation allowed)"),
    NG(0.00, "Fail (no compensation allowed)"),
    /** Pass in a module taken on a pass/fail basis; carries no quality points. */
    P(null, "Pass in a module taken on a pass/fail basis"),
    /** Failure in a module taken on a pass/fail basis; carries no quality points. */
    N(null, "Failure in a module taken on a pass/fail basis");

    private final Double qpv;
    private final String description;

    Grade(Double qpv, String description) {
        this.qpv = qpv;
        this.description = description;
    }

    /**
     * Returns the quality point value of this grade.
     *
     * <p>Returns {@code null} for P and N, whose QPV column in Table 2.1 is a
     * dash. Those grades are non-quality hours: they are left out of the QCA
     * calculation entirely rather than counted as zero, because recording them
     * as 0.00 would pull a student's QCA down for a module that was never
     * graded on the quality scale (Handbook 3.3.1.2).
     *
     * @return the QPV, or {@code null} when this grade has none
     */
    public Double getQpv() {
        return qpv;
    }

    /**
     * Returns the Table 2.1 description of this grade.
     *
     * @return the grade descriptor
     */
    public String getDescription() {
        return description;
    }

    /**
     * Reports whether this grade has a quality point value.
     *
     * <p>Use this to decide whether a result belongs in the QCA calculation.
     *
     * @return {@code true} unless this is P or N
     */
    public boolean hasQpv() {
        return qpv != null;
    }

    /**
     * Reports whether this grade counts as passed outright.
     *
     * <p>A grade is passed when it reaches the 40% pass line, which Table 2.1
     * places at C3 (Handbook 2.5.3), or when it is P. Note that D1 does not pass:
     * it is a compensating fail that still has to be dealt with by the
     * Examination Board. This is deliberately not the same question as
     * {@link #awardsCredits()}.
     *
     * @return {@code true} when no compensation or repeat is needed
     */
    public boolean isPass() {
        return this == P || (qpv != null && qpv >= C3.qpv);
    }

    /**
     * Reports whether credits have been awarded for this grade.
     *
     * <p>This follows the "Credits Awarded" column of Table 2.1, which is the
     * test the award condition in Handbook 2.6.1(b) applies. D1 awards credits
     * even though it is a fail, so a student may satisfy "full credits in all
     * prescribed modules" while still holding a grade that has to be resolved.
     *
     * @return {@code true} when the module earns its credits
     */
    public boolean awardsCredits() {
        return this != F && this != NG && this != N;
    }

    /**
     * Reports whether this is a deficient grade.
     *
     * <p>Handbook 2.5.4 lists the deficient grades as F, NG, I, I2, M and N:
     * credits have not been awarded and must be achieved before the student can
     * progress or graduate. D1 is a compensating fail rather than a deficient
     * one and is therefore excluded, which is why {@link #isPass()} cannot be
     * expressed as "not deficient".
     *
     * @return {@code true} for F, NG and N
     */
    public boolean isDeficient() {
        return this == F || this == NG || this == N;
    }

    /**
     * Reports whether this is the compensating fail grade.
     *
     * <p>D1 sits between a pass and a deficiency: the pass mark is 40% and D1
     * covers 35% to below 40% (Handbook 2.5.3), so the grade may be compensated
     * at the Examination Board's discretion (Handbook 3.3.7.2(a)).
     *
     * @return {@code true} only for D1
     */
    public boolean isCompensatingFail() {
        return this == D1;
    }

    /**
     * Returns this grade after the cap applied to annual repeats.
     *
     * <p>Grades awarded at the annual repeats are capped at C3 (Handbook
     * 3.2.6.1). Applying the cap only lowers a grade, never raises it, so a
     * student who already did better keeps their original grade. P and N are
     * returned unchanged: they are outside the quality point scale and the cap
     * has nothing to cap.
     *
     * @return C3 when this grade is worth more than C3, otherwise this grade
     */
    public Grade cappedAtC3() {
        if (qpv == null || qpv <= C3.qpv) {
            return this;
        }
        return C3;
    }

    /**
     * Returns the better of this grade and another.
     *
     * <p>When a module is repeated, the higher of the original and the repeat
     * grade stands (Handbook 3.2.6.1). Comparison uses the QPV rather than the
     * declaration order so that adding a grade later cannot silently change what
     * "better" means. Grades without a QPV cannot be ranked, so a grade that has
     * one is preferred; if neither has one, this grade is returned.
     *
     * @param other the grade to compare with, may be {@code null}
     * @return whichever grade carries more quality points
     */
    public Grade betterOf(Grade other) {
        if (other == null || !other.hasQpv()) {
            return this;
        }
        if (!hasQpv()) {
            return other;
        }
        return qpv >= other.qpv ? this : other;
    }
}

