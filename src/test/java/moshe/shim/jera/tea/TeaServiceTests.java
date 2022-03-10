package moshe.shim.jera.tea;

import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.exceptions.ValidationException;
import moshe.shim.jera.payload.WeightDTO;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.services.TeaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeaServiceTests extends TeaTestsUtils {

    @Autowired
    private TeaService teaService;

    @Test
    public void saveEntity_whenDTOIsValid_receiveDTOBack() {
        var savedDTO = teaService.addTea(psEntity.getId(), createValidTeaDTO());
        assertThat(savedDTO).isNotNull();
        assertThat(savedDTO).isInstanceOf(TeaDTO.class);
    }

    @Test
    public void saveEntity_whenDTOHasWeights_receiveTeaDTOWithWeightsDTO() {
        var savedDTO = teaService.addTea(psEntity.getId(), createValidTeaDTO());
        assertThat(savedDTO).isNotNull();
        assertThat(savedDTO.getWeights().stream().toList().get(0)).isInstanceOf(WeightDTO.class);
    }

    @Test
    public void saveEntity_whenDTOIsValid_saveEntityInDB() {
        var savedDTO = teaService.addTea(psEntity.getId(), createValidTeaDTO());
        assertThat(teaRepository.findById(savedDTO.getId())).isNotNull();
    }

    @Test
    public void saveEntity_whenDTOHasNullWeightAndPrice_throwValidationException() {
        var dto = createValidTeaDTO();
        dto.setWeights(null);
        dto.setPrice(null);
        assertThrows(ValidationException.class, () -> teaService.addTea(psEntity.getId(), dto));
    }

    @Test
    public void saveEntity_createTimeStampOnSave_receiveDTOWithUploadDate(){
        var dto = createValidTeaDTO();
        dto.setUploadDate(null);
        assertThat(teaService.addTea(psEntity.getId() ,dto).getUploadDate()).isNotNull();
    }

    @Test
    public void updateEntity_whenIdIsFoundInDB_updateDTOInDB() {
        var teaEntity = uploadTeaToDB();

        var teaDTO = createValidTeaDTO();
        var newName = "updated dto";
        teaDTO.setName(newName);
        teaService.updateTeaById(teaEntity.getId(), teaDTO);

        var byId = teaRepository.findById(teaEntity.getId()).orElse(null);
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(newName);
    }

    @Test
    public void updateEntityWeights_whenIdFound_updateWeights() {
        var teaEntity = uploadTeaToDB();

        var teaDTO = createValidTeaDTO();
        var weights = List.of(new WeightDTO(500, 20, false));
        teaDTO.setWeights(weights);
        teaService.updateTeaById(teaEntity.getId(), teaDTO);

        var byId = teaService.findById(teaEntity.getId());
        assertThat(byId.getWeights()).isEqualTo(weights);
    }

    @Test
    public void updateEntity_whenIdIsNotFoundInDB_throwResourceNotFoundException() {
        var dto = createValidTeaDTO();
        assertThrows(ResourceNotFoundException.class, () -> teaService.updateTeaById(100, dto));
    }


    @Test
    public void updateEntity_whenUpdateIsSuccessful_receiveString() {
        var teaEntity = uploadTeaToDB();

        TeaDTO validTeaDTO = createValidTeaDTO();
        var newName = "updated dto";
        validTeaDTO.setName(newName);

        assertThat(teaService.updateTeaById(teaEntity.getId(), validTeaDTO)).isInstanceOf(String.class);
    }

    @Test
    public void getDTOById_whenIdIsFoundInDB_receiveDTO() {
        var savedDTO = teaService.addTea(psEntity.getId(), createValidTeaDTO());
        assertThat(teaService.findById(savedDTO.getId())).isNotNull();
    }

    @Test
    public void getDTOById_whenIdIsNotFoundInDB_throwResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class,
                () -> teaService.findById(100));
    }

    @Test
    public void deleteEntity_whenIdIsFoundInDB_entityIsDeletedFromDB() {
        var teaEntity = uploadTeaToDB();

        teaService.deleteById(teaEntity.getId());
        assertThat(teaRepository.findById(teaEntity.getId()).orElse(null)).isNull();
    }

    @Test
    public void deleteEntity_whenIdIsNotFoundInDB_throwResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class,
                () -> teaService.deleteById(100));
    }

    @Test
    public void getAllFromDB_whenThereAreTwoEntities_receiveAListOfSizeOfTwo() {
        uploadTeaToDB();
        uploadTeaToDB();
        assertThat(teaService.getAll().size()).isEqualTo(2);
    }

    @Test
    public void getAllFromDB_whenThereAreNoEntities_receiveAnEmptyList() {
        assertThat(teaService.getAll().size()).isEqualTo(0);
    }

    @Test
    public void updateWeight_whenWeightExists_updateWeight() {
        var tea = uploadTeaToDB();

        var weight = tea.getWeights().get(0);
        var weightDTO = toWeightDTO(weight);
        weightDTO.setPrice(5000);
        teaService.updateWeight(
                tea.getId(),
                weightDTO);

        var teaById = teaRepository.findById(tea.getId()).orElse(null);
        assert teaById != null;
        assertThat(teaById
                .getWeights().stream()
                .anyMatch(w -> Objects.equals(w.getPrice(), weightDTO.getPrice())))
                .isTrue();
    }

    @Test
    public void updateWeight_whenWeightIsNotFound_throwResourceNotFountException() {
        var tea = uploadTeaToDB();

        var weight = tea.getWeights().get(0);
        var weightDTO = toWeightDTO(weight);
        weightDTO.setWeight(1000);
        weightDTO.setPrice(500);

        assertThrows(ResourceNotFoundException.class, () -> teaService.updateWeight(tea.getId(), weightDTO));
    }
}
