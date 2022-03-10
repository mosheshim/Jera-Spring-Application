package moshe.shim.jera.services.impl;

import lombok.val;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.services.TeaProductSeriesService;

import moshe.shim.jera.services.TeaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import static moshe.shim.jera.controllers.CoffeeController.API_1_COFFEE;
import static moshe.shim.jera.controllers.TeaProductSeriesController.API_1_PRODUCT_SERIES;

@Service
public class TeaProductSeriesServiceImpl implements TeaProductSeriesService {

    private final TeaProductSeriesRepository psRepository;
    private final TeaService teaService;
    private final TypeMap<TeaProductSeries, TeaProductSeriesDTO> toProductSeriesDTO;
    private final TypeMap<TeaProductSeriesDTO, TeaProductSeries> toProductSeries;

    public TeaProductSeriesServiceImpl(
            TeaProductSeriesRepository psRepository,
            TeaService teaService,
            ModelMapper modelMapper) {
        this.psRepository = psRepository;
        this.teaService = teaService;
        this.toProductSeries = modelMapper.createTypeMap(TeaProductSeriesDTO.class, TeaProductSeries.class);
        this.toProductSeriesDTO = modelMapper.createTypeMap(TeaProductSeries.class, TeaProductSeriesDTO.class);
    }

    @Override
    public TeaProductSeriesDTO addProductSeries(TeaProductSeriesDTO dto) {
        val teaProductSeries = toProductSeries.map(dto);
        teaProductSeries.setUploadDate(new Date());
        return toProductSeriesDTO.map(psRepository.save(teaProductSeries));
    }

    @Override
    public List<TeaProductSeriesDTO> getAll() {
        return psRepository
                .findAll().stream().map(ps -> {
                    val dto = toProductSeriesDTO.map(ps);
                    dto.setTeaList(teaService.findAllByPs(ps));
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public TeaProductSeriesDTO findById(long id) {
        var ps = findProductSeriesById(id);
        ps.setTeaList(teaService.findAllByPs(
                toProductSeries.map(ps)));
        return ps;
    }

    @Override
    public String deleteById(long id) {
        try {
            psRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product Series", "id", id, API_1_PRODUCT_SERIES);
        }
        return "Deleted Successfully";
    }

    @Override
    public String updateById(long id, TeaProductSeriesDTO dto) {
        var byId = findProductSeriesById(id);
        var beforeUpdated = toProductSeries.map(dto);

        beforeUpdated.setId(id);
        beforeUpdated.setUploadDate(byId.getUploadDate());

        psRepository.save(beforeUpdated);
        return "Updated successfully";
    }

    private TeaProductSeriesDTO findProductSeriesById(long id) {
        return toProductSeriesDTO.map(
                psRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product Series", "Id", id, API_1_COFFEE
                )));

    }
}

