package moshe.shim.jera.controllers;

import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.services.TeaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static moshe.shim.jera.controllers.TeaController.API_1_TEA;

@RestController
@RequestMapping(API_1_TEA)
public class TeaController {
    public final static String API_1_TEA = "/api/1/tea";

    private final TeaService service;

    public TeaController(TeaService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<TeaDTO>> getAllTea(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeaDTO> findById(@PathVariable("id") long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/product-series/{id}")
    public ResponseEntity<TeaDTO> addTea(@PathVariable("id") long productSeriesId ,@Valid  @RequestBody TeaDTO dto){
        return new ResponseEntity<>(service.addTea(productSeriesId, dto), HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<String> updateTea(@PathVariable("id") long productSeriesId ,@Valid  @RequestBody TeaDTO dto){
        return new ResponseEntity<>(service.updateTeaById(productSeriesId, dto), HttpStatus.NO_CONTENT);

    }

    @PutMapping({"/{id}/weight"})
    public ResponseEntity<String> updateWeight(
            @PathVariable("id") long productSeriesId ,@Valid  @RequestBody WeightDTO dto) {
        return new ResponseEntity<>(service.updateWeight(productSeriesId, dto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTea(@PathVariable("id") long id){
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.NO_CONTENT);
        }
}
