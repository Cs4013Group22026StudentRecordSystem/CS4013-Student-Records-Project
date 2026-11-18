package model;

/**
 * An academic staff member who is responsible for one or more modules.
 *
 * <p>A faculty member's department submits the final grades for the modules they
 * own (Handbook 3.2.2.1), which is why a {@link Module} refers to the staff
 * member rather than to a department name.
 */
public class Faculty extends Person {
    private final String department;

    /**
     * Creates a faculty member.
     *
     * @param personId unique staff identifier
     * @param name staff member's full name
     * @param email staff member's email address, or {@code null} when not on record
     * @param department department the staff member belongs to
     */
    public Faculty(String personId, String name, String email, String department) {
        super(personId, name, email);
        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException("department is required");
        }
        this.department = department.trim();
    }

    /**
     * Returns the department this staff member belongs to.
     *
     * @return the department name
     */
    public String getDepartment() {
        return department;
    }
}
