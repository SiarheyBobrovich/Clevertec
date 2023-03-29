package by.bobrovich.market.service.api;

import by.bobrovich.market.data.alcohol.request.RequestAlcoholDto;
import by.bobrovich.market.data.alcohol.response.ResponseAlcoholDto;

import java.util.List;

public interface AlcoholService {

    /**
     * Find an alcohol in the repository, map it to response dto and return
     * @param id alcohol ID
     * @return Found alcohol dto
     */
    ResponseAlcoholDto get(Long id);

    /**
     * Find all alcohols in the repository, map them and return list of dto
     * @return List of alcohol dto
     */
    List<ResponseAlcoholDto> getAll();

    /**
     * Map a request dto to alcohol and save into the repository
     * @param dto request dto
     */
    void save(RequestAlcoholDto dto);

    /**
     * Map a request dto to alcohol and update existed alcohol in repository
     * @param id alcohol ID
     * @param dto request dto
     */
    void update(Long id, RequestAlcoholDto dto);

    /**
     * Delete alcohol from repository by ID
     * @param id alcohol ID
     */
    void delete(Long id);
}
