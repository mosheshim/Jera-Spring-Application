package moshe.shim.jera.services.impl;

import lombok.val;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.entities.Weight;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.exceptions.ValidationException;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import moshe.shim.jera.services.TeaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static moshe.shim.jera.controllers.TeaController.API_1_TEA;
import static moshe.shim.jera.controllers.TeaProductSeriesController.API_1_PRODUCT_SERIES;

@Service
public class TeaServiceImpl implements TeaService {
    private final TeaRepository teaRepository;
    private final TeaProductSeriesRepository teaProductSeriesRepository;
    private final TypeMap<TeaDTO, Tea> toTea;
    private final TypeMap<Tea, TeaDTO> toDTO;
    private final TypeMap<WeightDTO,Weight> toWeightDTO;


    public TeaServiceImpl(
            TeaRepository teaRepository,
            TeaProductSeriesRepository teaProductSeriesRepository,
            ModelMapper modelMapper) {
        this.teaRepository = teaRepository;
        this.teaProductSeriesRepository = teaProductSeriesRepository;
        this.toTea = modelMapper.createTypeMap(TeaDTO.class, Tea.class);
        this.toDTO = modelMapper.createTypeMap(Tea.class, TeaDTO.class);
        this.toWeightDTO = modelMapper.createTypeMap(WeightDTO.class, Weight.class);
    }

    @Override
    public TeaDTO addTea(long id, TeaDTO teaDTO) {
        validateTea(teaDTO);
        val teaProductSeries = teaProductSeriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductSeries",
                        "id",
                        id,
                        String.format("/api/1/product-series/%s/tea", id))); //TODO add the api to a static thingy

        val tea = toTea.map(teaDTO);
        tea.setTeaProductSeries(teaProductSeries);
        val saved = teaRepository.save(tea);
        return toDTO.map(saved);
    }

    @Override
    public String updateTeaById(long id, TeaDTO dto) {
        Tea instanceById = findTeaById(id);

        var toSave = toTea.map(dto);
        toSave.setId(instanceById.getId());
        toSave.setUploadDate(instanceById.getUploadDate());
        toSave.setTeaProductSeries(instanceById.getTeaProductSeries());

        teaRepository.save(toSave);
        return "Updated Successfully";

    }

    @Override
    public String deleteById(long id) {
        try {
            teaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Product Series", "id", id, API_1_PRODUCT_SERIES);
        }
        return "Deleted Successfully";
    }

    @Override
    public TeaDTO findById(long id) {
        return toDTO.map(findTeaById(id));
    }

    @Override
    public List<TeaDTO> findAllByPs(TeaProductSeries ps) {
        return teaRepository.findAllByTeaProductSeries(ps)
                .stream().map(toDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeaDTO> getAll() {
        return teaRepository.findAll().stream()
                .map(toDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public String updateWeight(long id , WeightDTO weight) {
        var byId = findTeaById(id);

        if (byId.getWeights().removeIf(w->
                Objects.equals(w.getWeight(), weight.getWeight()))){

            byId.getWeights().add(toWeightDTO.map(weight));
            teaRepository.save(byId);
        }else
            throw new ResourceNotFoundException("weights", "weight", weight.getWeight(), API_1_TEA + id);

        return "Updated Successfully";
    }

    private Tea findTeaById(long id) {
        return teaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product Series", "Id", id, API_1_TEA + id));
    }

    private void validateTea(TeaDTO dto) {
        if (dto.getWeights().isEmpty() && dto.getPrice() == null)
            throw new ValidationException(
                    "Tea cant have no weights and no price",
                    "Price",
                    "null",
                    API_1_TEA+"/%s/tea");
        for (WeightDTO weight : dto.getWeights()) {
            if(weight.getInStock() == null || weight.getWeight() == null)
                throw new ValidationException(
                        "Weight cant have null fields",
                        "weight",
                        "null",
                        API_1_TEA+"/%s/tea"
                );
        }
    }
}
