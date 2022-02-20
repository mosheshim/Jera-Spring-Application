package moshe.shim.jera.services.impl;

import lombok.val;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.models.Weight;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import moshe.shim.jera.services.TeaService;
import moshe.shim.jera.utils.Mappers;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeaServiceImpl implements TeaService {
    private final TeaRepository teaRepository;
    private final TeaProductSeriesRepository teaProductSeriesRepository;

    private final TypeMap<TeaDTO, Tea> toTea;


    public TeaServiceImpl(TeaRepository teaRepository,
                          TeaProductSeriesRepository teaProductSeriesRepository,
                          ModelMapper modelMapper) {
        this.teaRepository = teaRepository;
        this.teaProductSeriesRepository = teaProductSeriesRepository;
        this.toTea = modelMapper.createTypeMap(TeaDTO.class, Tea.class);
    }

    @Override
    public TeaDTO addTea(long id, TeaDTO dto) throws RuntimeException {
        val teaProductSeries = teaProductSeriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductSeries",
                        "id",
                        id,
                        String.format("/api/1/product-series/%s/tea", id)));

        checkIfTeaValid(dto, teaProductSeries);

        val teaSet = new HashSet<Tea>();
        if (dto.getWeights() != null) teaSet.addAll(dto.getWeights().stream().map(w -> buildTeaTDO(
                dto, teaProductSeries, w)).collect(Collectors.toSet()));
        else teaSet.add(buildTeaTDO(dto, teaProductSeries, null));
        return Mappers.toTeaDTO(teaRepository.saveAll(teaSet));
    }

    private Tea buildTeaTDO(TeaDTO dto, TeaProductSeries productSeries, Weight weight) {
        val t = toTea.map(dto);
        t.setTeaProductSeries(productSeries);
        if (dto.getWeights() != null) {
            t.setInStock(weight.getInStock());
            t.setPrice(weight.getPrice());
            t.setWeight(weight.getWeight());
        }
        return t;
    }

    private void checkIfTeaValid(TeaDTO dto, TeaProductSeries teaProductSeries) {
        Set<Tea> teaSet = teaRepository.findAllByNameAndTeaProductSeries(dto.getName(), teaProductSeries);
        if (!teaSet.isEmpty()) throw new RuntimeException("Tea already exists for this product series");
    }


//    @Override
//    public Set<TeaDTO> getAllTea() {
//        return teaRepository.findAll().stream().map(Mappers::toTeaDTO)
//                .collect(Collectors.toList());
//    }
}
