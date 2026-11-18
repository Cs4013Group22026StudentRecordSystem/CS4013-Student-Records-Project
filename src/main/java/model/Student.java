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
    private final Programme programme;

    /**
     * Creates a student whose email address is not on record.
     *
     * @param studentId student's identifier
     * @param name student's full name
     * @param programme programme the student is registered on
     */
    public Student(String studentId, String name, Programme programme) {
        this(studentId, name, null, programme);
    }

    /**
     * Creates a student.
     *
     * @param studentId student's identifier
     * @param name student's full name
     * @param email student's email address, or {@code null} when not on record
     * @param programme programme the student is registered on
     */
    public Student(String studentId, String name, String email, Programme programme) {
        super(studentId, name, email);
        this.programme = programme;
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
}
