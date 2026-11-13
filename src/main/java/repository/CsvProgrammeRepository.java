package repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import model.Programme;
import model.ProgrammeLevel;

/**
 * Loads programmes from {@code programmes.csv} and provides in-memory lookups.
 */
public class CsvProgrammeRepository implements IProgrammeRepository {
    private final Path filesPath;
    private final Map<String, Programme> programmesById;

    /**
     * Creates a repository and loads programmes from the specified data directory.
     *
     * @param filesPath directory containing {@code programmes.csv}
     * @throws RuntimeException if the programme file cannot be read or contains invalid data
     */
    public CsvProgrammeRepository(String filesPath) {
        this.filesPath = Path.of(filesPath);
        try {
            this.programmesById = loadProgrammes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load programmes from CSV", e);
        }
    }

    /**
     * Reads and validates programme records from {@code programmes.csv}.
     *
     * @return programmes indexed by programme code
     * @throws IOException if the file cannot be read or contains invalid data
     */
    public Map<String, Programme> loadProgrammes() throws IOException {
        Map<String, Programme> programmes = new HashMap<>();
        Path programmesFile = filesPath.resolve("programmes.csv");

        try (BufferedReader reader = Files.newBufferedReader(programmesFile, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Missing header in " + programmesFile);
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
                            "Expected 3 columns in " + programmesFile + " at line " + lineNumber);
                }

                // clean the fields
                String code = fields[0].trim();
                String name = fields[1].trim();
                String levelName = fields[2].trim();
                // check empty fields
                if (code.isEmpty() || name.isEmpty() || levelName.isEmpty()) {
                    throw new IOException("Empty required field in " + programmesFile
                            + " at line " + lineNumber);
                }

                ProgrammeLevel level;
                // Throw an exception if the programme level is invalid
                try {
                    level = ProgrammeLevel.valueOf(levelName);
                } catch (IllegalArgumentException exception) {
                    throw new IOException("Invalid programme level '" + levelName + "' in "
                            + programmesFile + " at line " + lineNumber, exception);
                }

                Programme programme = new Programme(code, name, level);
                // Check for duplicate Programme and throw an exception if found
                if (programmes.putIfAbsent(code, programme) != null) {
                    throw new IOException("Duplicate programme code '" + code + "' in "
                            + programmesFile + " at line " + lineNumber);
                }
            }
        }

        return programmes;
    }

    /**
     * Finds a programme by its code.
     *
     * @param code programme code to look up
     * @return the matching programme, or {@code null} if none exists
     */
    public Programme findByCode(String code) {
        return programmesById.get(code);
    }

    /**
     * Returns all programmes loaded from the CSV file.
     *
     * @return an unmodifiable list of programmes
     */
    public List<Programme> findAll() {
        return List.copyOf(programmesById.values());
    }

    /**
     * Placeholder; this implementation does not yet persist programmes to the CSV file.
     *
     * @param programme programme to save
     */
    public void save(Programme programme) {
        // TODO: implement saving logic
    }
}
