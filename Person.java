public abstract class Person {
    private String name;
    private String personId;

    /**
        * Constructor for Person class.
        * @param name The name of the person.
        * @param personId The unique identifier for the person.
     */
    public Person(String name, String personId) {
        this.name = name;
        this.personId = personId;
    }

    /**
        * Gets the unique identifier of the person.
        * @return The unique identifier of the person.
     */
    public String getPersonId() {
        return personId;
    }

    /**
        * Gets the name of the person.
        * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    public abstract String generateTranscript();

}