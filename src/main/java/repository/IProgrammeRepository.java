package repository;

import java.util.List;

import model.Programme;

/**
 * Provides access to programme data.
 *
 * <p>Implementations may persist programmes in CSV files or keep them in memory,
 * for example when testing.
 */
public interface IProgrammeRepository {
    /**
     * Finds a programme by its code.
     *
     * @param code programme code to look up
     * @return the matching programme, or {@code null} if none exists
     */
    public Programme findByCode(String code);

    /**
     * Returns all available programmes.
     *
     * @return the available programmes
     */
    public List<Programme> findAll();

    /**
     * Saves a programme to the repository.
     *
     * @param programme programme to save
     */
    public void save(Programme programme);
}
