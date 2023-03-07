package by.bobrovich.market.dao.api;

import by.bobrovich.market.entity.Alcohol;
import by.bobrovich.market.exceptions.AlcoholNotFoundException;

import java.util.List;

public interface AlcoholDao {
    /**
     * Find an alcohol by ID in the repository
     * @param id alcohol ID
     * @return current Alcohol
     * @throws AlcoholNotFoundException if alcohol does not exist
     */
    Alcohol get(Long id) throws AlcoholNotFoundException;

    /**
     * Find all alcohols in the repository
     * @return List of all alcohols
     */
    List<Alcohol> getAll();

    /**
     * Save a new alcohol in the repository
     * @param alcohol a new Alcohol without ID
     * @return generated ID
     */
    Long save(Alcohol alcohol);

    /**
     * Update all parameters in alcohol
     * @param alcohol updated alcohol
     */
    void update(Alcohol alcohol);

    /**
     * Delete an alcohol by ID
     * @param id alcohol ID
     */
    void delete(Long id);
}
