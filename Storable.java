import java.io.IOException;

/**
 * Defines CSV-backed persistence operations.
 */
public interface Storable {
    /** Loads persisted application data. */
    void read() throws IOException;

    /** Writes the current application data. */
    void write() throws IOException;

    /** Persists the results for the module whose student result changed. */
    void update(Student student, Module module) throws IOException;
}
