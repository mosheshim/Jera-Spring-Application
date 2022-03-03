package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static moshe.shim.jera.controllers.TeaProductSeriesController.API_1_PRODUCT_SERIES;

@RequestMapping(API_1_PRODUCT_SERIES)
@RestController
public class TeaProductSeriesController {
    public final static String API_1_PRODUCT_SERIES =  "/api/1/product-series";

    private final TeaProductSeriesService service;

    public TeaProductSeriesController(TeaProductSeriesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TeaProductSeriesDTO> addTeaProductSeries(@Valid @RequestBody TeaProductSeriesDTO dto) {
        return new ResponseEntity<>(service.addProductSeries(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TeaProductSeriesDTO>> getTeaProductSeries(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeaProductSeriesDTO> getTeaProductSeriesById(@PathVariable long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeaProductSeries(
            @PathVariable long id, @Valid @RequestBody TeaProductSeriesDTO dto){
        return new ResponseEntity<>(service.updateById(id, dto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateTeaProductSeries(@PathVariable long id){
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
