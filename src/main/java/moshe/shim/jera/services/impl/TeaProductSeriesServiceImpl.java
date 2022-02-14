package moshe.shim.jera.services.impl;

import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeaProductSeriesServiceImpl implements TeaProductSeriesService {

    private final TeaProductSeriesRepository repo;

    public TeaProductSeriesServiceImpl(TeaProductSeriesRepository repo) {
        this.repo = repo;
    }

    @Override
    public TeaProductSeriesDTO addTeaProductSeries(TeaProductSeriesDTO dto) {
        return repo.save(dto.fromDTO()).toDTO();
    }

    @Override
    public List<TeaProductSeriesDTO> getAllTeaProductSeries() {
        return repo.findAll().stream().map(TeaProductSeries::toDTO)
                .collect(Collectors.toList());
    }
}
