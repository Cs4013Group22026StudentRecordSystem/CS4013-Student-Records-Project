package model;

/**
 * A student registered on a programme.
 *
 * <p><strong>Association ownership.</strong> Results are not owned by the
 * student. The class diagram draws the association in one direction only
 * ({@code Result "0..*" --> "1" Student}): a result knows which student it
 * belongs to, while the student keeps no collection of results.
 *
 * <p>As a consequence a {@code Student} cannot answer "what are my grades" by
 * itself. Callers read results from the result repository and hand them to
 * whichever service needs them (see {@code IResultRepository}). That is
 * deliberate rather than unfinished: giving each association exactly one owner
 * means the two classes can never hold divergent copies of the same data, and
 * it keeps the model free of persistence concerns so the objects stay testable
 * without a data source.
 */
public class Student extends Person {
    private static final int FIRST_YEAR = 1;

    private final Programme programme;
    private int currentYear;

    /**
     * Creates a first-year student whose email address is not on record.
     *
     * @param studentId student's identifier
     * @param name student's full name
     * @param programme programme the student is registered on
     */
    public Student(String studentId, String name, Programme programme) {
        this(studentId, name, null, programme, FIRST_YEAR);
    }

    /**
     * Creates a student whose email address is not on record.
     *
     * @param studentId student's identifier
     * @param name student's full name
     * @param programme programme the student is registered on
     * @param currentYear year of study the student has reached
     */
    public Student(String studentId, String name, Programme programme, int currentYear) {
        this(studentId, name, null, programme, currentYear);
    }

    /**
     * Creates a student.
     *
     * @param studentId student's identifier
     * @param name student's full name
     * @param email student's email address, or {@code null} when not on record
     * @param programme programme the student is registered on
     * @param currentYear year of study the student has reached
     */
    public Student(String studentId, String name, String email, Programme programme, int currentYear) {
        super(studentId, name, email);
        this.programme = programme;
        this.currentYear = requireValidYear(currentYear);
    }

    /**
     * Returns this student's identifier.
     *
     * <p>A student identifier is the same value as {@link #getPersonId()}; this
     * accessor exists so calling code can use the domain vocabulary.
     *
     * @return student ID
     */
    public String getStudentId() {
        return getPersonId();
    }

    /**
     * Returns the programme in which this student is enrolled.
     *
     * @return the student's programme
     */
    public Programme getProgramme() {
        return programme;
    }

    /**
     * Returns the year of which this student in.
     *
     * @return the student's current year.
     */
    public int getCurrentYear(){
        return currentYear;
    }

    /**
     * Moves this student into the next year of study.
     *
     * <p>The only caller is a progression decision of {@link Decision#PROGRESS}:
     * a repeat decision leaves the year unchanged, and a terminated enrolment
     * leaves the programme. Grading, repeat and termination rules are in
     * Handbook 3.3.5 to 3.3.7.
     *
     * @throws IllegalStateException when the student is already in the final year
     *         of their programme
     */
    public void advanceYear() {
        if (programme != null && currentYear >= programme.getDurationYears()) {
            throw new IllegalStateException(
                    "already in the final year of " + programme.getProgrammeCode());
        }
        currentYear++;
    }

    private int requireValidYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("currentYear must be at least 1");
        }
        if (programme != null && year > programme.getDurationYears()) {
            throw new IllegalArgumentException("currentYear " + year + " exceeds the "
                    + programme.getDurationYears() + "-year programme");
        }
        return year;
    }
}
