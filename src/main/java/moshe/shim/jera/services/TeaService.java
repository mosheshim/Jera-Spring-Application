package moshe.shim.jera.services;

import moshe.shim.jera.entities.TeaProductSeries;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.WeightDTO;

import java.util.List;

public interface TeaService {
    TeaDTO addTea(long id , TeaDTO teaDTO);
    String updateTeaById(long id, TeaDTO teaDTO);
    String deleteById(long id);
    TeaDTO findById(long id);
    List<TeaDTO> findAllByPs(TeaProductSeries ps);
    List<TeaDTO> getAll();
    String updateWeight(long id, WeightDTO weight);
}
