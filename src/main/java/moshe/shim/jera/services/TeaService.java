package moshe.shim.jera.services;

import moshe.shim.jera.payload.TeaDTO;

public interface TeaService {
    TeaDTO addTea(long id , TeaDTO teaDTO);
//    Set<TeaDTO> getAllTea();
}
