package by.bobrovich.market.cache.service.api;

import by.bobrovich.market.cache.data.request.RequestAlcoholDto;
import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;

public interface AlcoholService {

    ResponseAlcoholDto get(Long id);
    long save(RequestAlcoholDto dto);
    void update(Long id, RequestAlcoholDto dto);
    void delete(Long id);
}
