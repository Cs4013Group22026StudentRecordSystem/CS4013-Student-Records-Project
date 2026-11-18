package model;

/**
 * The outcome of a progression review.
 *
 * <p>The repeat and termination options mirror Handbook 3.3.5.2 (after the
 * autumn semester), 3.3.6.2 (after the spring semester) and 3.3.7.2 (after the
 * annual repeats). {@link #TERMINATE_ENROLMENT} is the {@code d} option in all
 * three regulations and is required: without it a review that ends a student's
 * enrolment cannot be represented.
 */
public enum Decision {
    PROGRESS,
    COMPENSATED_PASS,
    REPEAT_MODULE,
    REPEAT_SEMESTER,
    REPEAT_YEAR,
    TERMINATE_ENROLMENT
}
