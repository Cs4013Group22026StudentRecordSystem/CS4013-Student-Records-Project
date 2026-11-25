import java.io.IOException;

/**
 * Defines persistence operations for application data and module grades.
 */
public interface Storable {
    /** Loads persisted application data. */
    void read() throws IOException;

    /** Writes the current application data. */
    void write() throws IOException;

    /** Persists the student's current grade for the specified module. */
    void update(Student student, Module module) throws IOException;
}
