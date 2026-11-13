package repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Programme;
import model.Student;



/**
 * Loads student and programme records from CSV files and provides student lookups.
 */
public class CsvStudentRepository implements IStudentRepository {
    private final Path filesPath;
    private final Map<String, Student> studentsById;
    private final IProgrammeRepository programmeRepository;


    /**
     * Creates a repository using the CSV files in the given directory.
     *
     * @param filesPath directory containing {@code students.csv} and {@code programmes.csv}
     * @throws IOException if a file cannot be read or contains invalid data
     */
    public CsvStudentRepository(String filesPath, IProgrammeRepository programmeRepository) throws IOException {
        this.filesPath = Path.of(filesPath);
        this.programmeRepository = programmeRepository;
        this.studentsById = loadStudents();
    }

    /**
     * Reads students and their associated programmes from the CSV files.
     *
     * @return students indexed by student ID
     * @throws IOException if the student or programme data is invalid
     */
    private Map<String, Student> loadStudents() throws IOException {
        Map<String, Student> students = new HashMap<>();
        Path studentsFile = filesPath.resolve("students.csv");

        try (BufferedReader reader = Files.newBufferedReader(studentsFile, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Missing header in " + studentsFile);
            }

            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }

                String[] fields = CsvParser.parseLine(line);
                if (fields.length != 3) {
                    throw new IOException(
                            "Expected 3 columns in " + studentsFile + " at line " + lineNumber);
                }

                String studentId = fields[0].trim();
                String name = fields[1].trim();
                String programmeCode = fields[2].trim();
                if (studentId.isEmpty() || name.isEmpty() || programmeCode.isEmpty()) {
                    throw new IOException(
                            "Empty required field in " + studentsFile + " at line " + lineNumber);
                }

                Programme programme = programmeRepository.findByCode(programmeCode);
                // Check if the programme code exists in the loaded programmes
                if (programme == null) {
                    throw new IOException("Unknown programme code '" + programmeCode + "' in "
                            + studentsFile + " at line " + lineNumber);
                }

                Student student = new Student(studentId, name, programme);
                // Check for duplicate student ID keys and throw an exception if found
                if (students.putIfAbsent(studentId, student) != null) {
                    throw new IOException("Duplicate student ID '" + studentId + "' in "
                            + studentsFile + " at line " + lineNumber);
                }
            }
        }

        return students;
    }

    /**
     * Reads programme records from {@code programmes.csv}.
     *
     * @return programmes indexed by programme code
     * @throws IOException if the file cannot be read or contains invalid data
     */


    /**
     * Finds a student by ID.
     *
     * @param id student ID to look up
     * @return the matching student, or {@code null} if no student has that ID
     */
    public Student findById(String id) {
        return studentsById.get(id);
    }

    /**
     * Placeholder for saving a student; this implementation does not persist changes yet.
     *
     * @param student student to save
     */
    public void save(Student student) {
        // TODO: Implementation to save a student to the CSV file
    }

    /**
     * Returns all students loaded by this repository.
     *
     * @return an unmodifiable list of students
     */
    public List<Student> findAll() {
        return List.copyOf(studentsById.values());
    }
}
