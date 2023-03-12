package by.bobrovich.market.service;

import by.bobrovich.market.dao.api.AlcoholDao;
import by.bobrovich.market.data.alcohol.request.RequestAlcoholDto;
import by.bobrovich.market.data.alcohol.response.ResponseAlcoholDto;
import by.bobrovich.market.entity.Alcohol;
import by.bobrovich.market.exceptions.AlcoholNotFoundException;
import by.bobrovich.market.mapper.AlcoholMapper;
import by.bobrovich.market.service.api.AlcoholService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AlcoholServiceImpl implements AlcoholService {

    private final AlcoholDao dao;
    private final AlcoholMapper mapper = Mappers.getMapper(AlcoholMapper.class);
    @Override
    public ResponseAlcoholDto get(Long id) {
        Alcohol alcohol = dao.get(id);
        return mapper.alcoholToResponseAlcoholDto(alcohol);
    }

    @Override
    public List<ResponseAlcoholDto> getAll() {
        List<Alcohol> alcohols = dao.getAll();
        return alcohols.stream()
                .map(mapper::alcoholToResponseAlcoholDto)
                .toList();

    }

    @Override
    public void save(RequestAlcoholDto dto) {
        Alcohol convert = mapper.requestAlcoholDtoToAlcohol(dto);
        dao.save(convert);
    }

    @Override
    public void update(Long id, RequestAlcoholDto dto) {
        Alcohol convert = mapper.requestAlcoholDtoToAlcohol(dto);
        convert.setId(id);
        try {
            dao.update(convert);
        }catch (AlcoholNotFoundException e) {
            log.info("Update::{}", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
}
