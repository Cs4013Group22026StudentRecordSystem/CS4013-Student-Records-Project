import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a faculty member who teaches one module and grades the students
 * taking it.
 */
public class Faculty extends Person {
    private Module module;
    private ArrayList<Student> students;
    private final Storable storable;

    /**
     * Creates a faculty member with no module assigned.
     *
     * @param name     the faculty member's name
     * @param personId the faculty member's unique identifier
     */
    public Faculty(String name, String personId, Storable storable) {
        super(name, personId);
        if (storable == null) {
            throw new IllegalArgumentException("Storable cannot be null.");
        }
        this.storable = storable;
        this.students = new ArrayList<>();
    }

    /**
     * Gets the module this faculty member teaches.
     *
     * @return the taught module, or {@code null} if none is assigned
     */
    public Module getModule() {
        return module;
    }

    /**
     * Assigns the module this faculty member teaches. A faculty member teaches one
     * module at a time and may only switch to another module while no student has
     * been graded yet.
     *
     * @param module the module to teach
     * @throws IllegalArgumentException if the module is null, already assigned, or
     *                                  a different module is requested after
     *                                  grading has started
     */
    public void chooseAModuleToTeach(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null.");
        }
        if (this.module != null && this.module.getCode().equals(module.getCode())) {
            throw new IllegalArgumentException(
                    "This module is already assigned to this faculty member.");
        }
        if (!students.isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot change module after students have been graded.");
        }
        this.module = module;
    }

    /**
     * Records a grade for a student on the module this faculty member teaches. The
     * module is implied by the faculty member, so only the student and the grade
     * are supplied.
     *
     * @param student the student receiving the grade
     * @param grade   the grade to record
     * @throws IllegalStateException    if no module has been assigned
     * @throws IllegalArgumentException if the student or grade is null, or the
     *                                  student's programme does not include this
     *                                  module
     * @throws IOException if the result cannot be persisted
     */
    public void setResult(Student student, Grade grade) throws IOException {
        if (module == null) {
            throw new IllegalStateException("No module assigned to this faculty member.");
        }
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }
        if (!isRegisteredForModule(student, module)) {
            throw new IllegalArgumentException(
                    "Student is not registered for this module.");
        }
        student.receiveGrade(grade, module);
        addStudent(student);
        storable.update(student, module);
    }

    private boolean isRegisteredForModule(Student student, Module module) {
        return module.getCode() != null
                && student.getRegisteredModule(module.getCode()) != null;
    }

    /**
     * Associates a student with this faculty member. {@link #setResult} does this
     * automatically; it is also used by the CSV loader to restore the relationship.
     *
     * @param student the student to associate
     */
    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    /**
     * Returns the students this faculty member has graded.
     *
     * @return a copy of the students
     */
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public boolean addStudentOnModule(Student student) {
        return false; // Placeholder implementation, to be completed based on specific requirements
    }

    public boolean removeStudentOnModule(Student student) {
        return false; // Placeholder implementation, to be completed based on specific requirements
    }

    /**
     * Generates a summary of the module this faculty member teaches and the
     * students they grade.
     *
     * @return the formatted faculty transcript
     */
    @Override
    public String generateTranscript() {
        if (module == null) {
            return "No module assigned.";
        }

        StringBuilder transcript = new StringBuilder();
        transcript.append("Module: ")
                .append(module.getName())
                .append(" (")
                .append(module.getCode())
                .append(")")
                .append(System.lineSeparator());
        // TODO: Include each student's grade in the module transcript.
        for (Student student : students) {
            transcript.append(student.getName())
                    .append(" (").append(student.getPersonId()).append(")")
                    .append(System.lineSeparator());
        }
        return transcript.toString();
    }
}
