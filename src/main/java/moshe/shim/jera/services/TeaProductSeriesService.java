package moshe.shim.jera.services;

import moshe.shim.jera.payload.TeaProductSeriesDTO;

import java.util.List;
import java.util.Set;

public interface TeaProductSeriesService {
    TeaProductSeriesDTO addProductSeries(TeaProductSeriesDTO teaProductSeriesDTO);
    List<TeaProductSeriesDTO> getAll();
    TeaProductSeriesDTO findById(long id);
    String deleteById(long id);
    String updateById(long id, TeaProductSeriesDTO dto);
}
