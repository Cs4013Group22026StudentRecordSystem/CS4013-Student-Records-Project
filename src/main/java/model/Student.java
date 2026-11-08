package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student, their programme, and their module results.
 */
public class Student {
    private String studentId;
    private String name;
    private Programme programme;
    private List<ModuleResult> results = new ArrayList<>();

    /**
     * Creates a student enrolled in a programme.
     *
     * @param studentId student's identifier
     * @param name student's name
     * @param programme student's programme
     */
    public Student(String studentId, String name, Programme programme) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
    }

    /**
     * Adds a module result to this student's results.
     *
     * @param result result to add
     */
    public void addResult(ModuleResult result) {
        results.add(result);
    }

    /**
     * Returns this student's module results.
     *
     * @return the list of module results
     */
    public List<ModuleResult> getResults() {
        return results;
    }

    /**
     * Returns this student's identifier.
     *
     * @return student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Returns this student's name.
     *
     * @return student name
     */
    public String getName() {
        return name;
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
