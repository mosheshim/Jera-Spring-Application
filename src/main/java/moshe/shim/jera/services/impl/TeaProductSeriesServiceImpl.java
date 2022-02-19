package moshe.shim.jera.services.impl;

import lombok.val;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.services.TeaProductSeriesService;
import moshe.shim.jera.utils.Mappers;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeaProductSeriesServiceImpl implements TeaProductSeriesService {

    private final TeaProductSeriesRepository repo;
    private final TypeMap<TeaProductSeries, TeaProductSeriesDTO> toProductSeriesDTO;
    private final TypeMap<TeaProductSeriesDTO, TeaProductSeries> toProductSeries;
    private final Converter<Set<Tea>, Set<TeaDTO>> toTeaDTO =
            ctx -> Mappers.toProductSeriesDTOTeaDTO(ctx.getSource());

    public TeaProductSeriesServiceImpl(TeaProductSeriesRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.toProductSeries = modelMapper.createTypeMap(TeaProductSeriesDTO.class, TeaProductSeries.class);
        this.toProductSeriesDTO = modelMapper.createTypeMap(TeaProductSeries.class, TeaProductSeriesDTO.class)
                .addMappings(mapper -> mapper
                        .using(toTeaDTO).map(TeaProductSeries::getTeaSet, TeaProductSeriesDTO::setTeaList));

    }

    @Override
    public TeaProductSeriesDTO addTeaProductSeries(TeaProductSeriesDTO dto) {
        val teaProductSeries = toProductSeries.map(dto);
        return toProductSeriesDTO.map(repo.save(teaProductSeries));
    }

    @Override
    public Set<TeaProductSeriesDTO> getAllTeaProductSeries() {
        return repo.findAll().stream().map(toProductSeriesDTO::map).
                collect(Collectors.toSet());
    }
}

