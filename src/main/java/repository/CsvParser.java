package repository;

/**
 * Parses simple comma-separated lines that do not contain quoted commas.
 */
public class CsvParser {
    /**
     * Splits a CSV line into fields while preserving empty trailing fields.
     *
     * @param line line to parse
     * @return the fields in the line in a String array
     */
    public static String[] parseLine(String line) {
        // Simple CSV parsing logic, assuming no commas within fields
        // future improvement: handle quoted fields and escaped characters
        return line.split(",", -1);
    }
}
