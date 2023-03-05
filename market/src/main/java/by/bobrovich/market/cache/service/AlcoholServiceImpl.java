package by.bobrovich.market.cache.service;

import by.bobrovich.market.cache.dao.api.AlcoholDao;
import by.bobrovich.market.cache.data.request.RequestAlcoholDto;
import by.bobrovich.market.cache.data.response.ResponseAlcoholDto;
import by.bobrovich.market.cache.entity.Alcohol;
import by.bobrovich.market.cache.service.api.AlcoholService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlcoholServiceImpl implements AlcoholService {

    private final AlcoholDao dao;
    private final ConversionService conversionService;

    @Override
    public ResponseAlcoholDto get(Long id) {
        Alcohol alcohol = dao.get(id);
        return conversionService.convert(alcohol, ResponseAlcoholDto.class);
    }

    @Override
    public long save(RequestAlcoholDto dto) {
        Alcohol convert = conversionService.convert(dto, Alcohol.class);

        assert convert != null;
        return dao.save(convert);
    }

    @Override
    public void update(Long id, RequestAlcoholDto dto) {
        Alcohol convert = conversionService.convert(dto, Alcohol.class);
        assert convert != null;
        convert.setId(id);
        dao.update(convert);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}
