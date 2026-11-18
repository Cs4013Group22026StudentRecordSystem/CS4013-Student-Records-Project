package model;

/**
 * Anyone the system holds records about.
 *
 * <p>Students and staff share an identity (identifier, name and contact email),
 * so that identity lives here rather than being duplicated in each subclass.
 */
public abstract class Person {
    private final String personId;
    private final String name;
    private final String email;

    /**
     * Creates a person.
     *
     * @param personId unique identifier for this person
     * @param name person's full name
     * @param email person's email address, or {@code null} when not on record
     */
    protected Person(String personId, String name, String email) {
        if (personId == null || personId.isBlank()) {
            throw new IllegalArgumentException("personId is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        this.personId = personId.trim();
        this.name = name.trim();
        this.email = email == null || email.isBlank() ? null : email.trim();
    }

    /**
     * Returns this person's identifier.
     *
     * @return the identifier
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Returns this person's full name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns this person's email address.
     *
     * @return the email address, or {@code null} when not on record
     */
    public String getEmail() {
        return email;
    }
}
