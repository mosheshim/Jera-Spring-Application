package moshe.shim.jera.services;

import moshe.shim.jera.payload.TeaProductSeriesDTO;

import java.util.List;

public interface TeaProductSeriesService {
    TeaProductSeriesDTO addTeaProductSeries(TeaProductSeriesDTO teaProductSeriesDTO);
    List<TeaProductSeriesDTO> getAllTeaProductSeries();
}
