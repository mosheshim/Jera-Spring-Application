package moshe.shim.jera.services;

import moshe.shim.jera.payload.TeaProductSeriesDTO;

import java.util.List;
import java.util.Set;

public interface TeaProductSeriesService {
    TeaProductSeriesDTO addTeaProductSeries(TeaProductSeriesDTO teaProductSeriesDTO);
    Set<TeaProductSeriesDTO> getAllTeaProductSeries();
    TeaProductSeriesDTO getProductSeriesByID(long id);
}
