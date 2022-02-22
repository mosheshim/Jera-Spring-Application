package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<TeaProductSeriesDTO> addTeaProductSeries(@Valid @RequestBody TeaProductSeriesDTO dto) {
        return new ResponseEntity<>(service.addTeaProductSeries(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<TeaProductSeriesDTO>> getTeaProductSeries(){
        return ResponseEntity.ok(service.getAllTeaProductSeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeaProductSeriesDTO> getTeaProductSeriesById(@PathVariable long id){
        return ResponseEntity.ok(service.getProductSeriesByID(id));
    }
}
