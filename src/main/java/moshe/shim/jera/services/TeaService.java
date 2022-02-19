package moshe.shim.jera.services;

import moshe.shim.jera.payload.TeaDTO;

import java.util.List;
import java.util.Set;

public interface TeaService {
    TeaDTO addTea(long id ,TeaDTO teaDTO);
//    Set<TeaDTO> getAllTea();
}
