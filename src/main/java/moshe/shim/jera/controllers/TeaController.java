package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.services.TeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/1")
public class TeaController {
    private final TeaService service;

    public TeaController(TeaService service) {
        this.service = service;
    }

//    @GetMapping("/tea")
//    public ResponseEntity<Set<TeaDTO>> getAllTea(){
//        return ResponseEntity.ok(service.getAllTea());
//    }

    @PostMapping("/product-series/{id}/tea")
    public ResponseEntity<TeaDTO> addTea(@Valid @PathVariable("id") long productSeriesId , @RequestBody TeaDTO dto){
        return ResponseEntity.ok(service.addTea(productSeriesId, dto));
    }
}
