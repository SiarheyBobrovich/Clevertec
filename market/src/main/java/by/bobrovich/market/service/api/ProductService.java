package by.bobrovich.market.service.api;

import by.bobrovich.market.data.product.request.RequestProductDto;
import by.bobrovich.market.data.product.response.ResponseProductDto;

import java.util.List;

public interface ProductService {
    /**
     * Find a product in repository by ID
     * @param id product ID
     * @return a current product
     */
    ResponseProductDto getById(Integer id);

    /**
     * Find all products int repository
     * @return all products from repository
     */
    List<ResponseProductDto> getAll();

    /**
     * Save a new product in repository
     * @param dto product dto to save
     */
    void save(RequestProductDto dto);

    /**
     * Update all fields of product by ID
     * @param id a product ID
     * @param dto product dto to update
     */
    void update(Integer id, RequestProductDto dto);

    /**
     * Delete a product from repository by ID
     * @param id a product ID
     */
    void delete(Integer id);
}
