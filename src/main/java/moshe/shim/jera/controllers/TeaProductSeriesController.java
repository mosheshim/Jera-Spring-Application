package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/api/1/product-series")
@RestController
public class TeaProductSeriesController {

    private final TeaProductSeriesService service;

    public TeaProductSeriesController(TeaProductSeriesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TeaProductSeriesDTO> addTeaProductSeries(@RequestBody TeaProductSeriesDTO dto) {
        return ResponseEntity.ok(service.addTeaProductSeries(dto));
    }

    @GetMapping
    public ResponseEntity<Set<TeaProductSeriesDTO>> getTeaProductSeries(){
        return ResponseEntity.ok(service.getAllTeaProductSeries());
    }
}
