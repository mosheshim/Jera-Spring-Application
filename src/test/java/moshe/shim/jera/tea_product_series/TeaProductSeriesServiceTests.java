package moshe.shim.jera.tea_product_series;


import moshe.shim.jera.exceptions.ResourceNotFoundException;
import moshe.shim.jera.payload.TeaDTO;
import moshe.shim.jera.payload.TeaProductSeriesDTO;
import moshe.shim.jera.repositories.TeaProductSeriesRepository;
import moshe.shim.jera.repositories.TeaRepository;
import moshe.shim.jera.services.TeaProductSeriesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static moshe.shim.jera.TestUtils.*;
import static moshe.shim.jera.TestUtils.createValidPSEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestEntityManager
public class TeaProductSeriesServiceTests  extends TeaProductSeriesUtils{

    @Autowired
    private TeaProductSeriesService psService;

    private final String newName = "new Name";

    @Autowired
    public TeaProductSeriesServiceTests(
            TeaRepository teaRepository, TeaProductSeriesRepository psRepository, TestEntityManager entityManager) {
        super(teaRepository, psRepository, entityManager);
    }

    @Test
    public void saveEntity_whenDTOIsValid_receiveDTOBack() {
        var savedDTO = psService.addProductSeries(createValidPSDTO());
        assertThat(savedDTO).isNotNull();
    }

    @Test
    public void saveEntity_whenDTOIsValid_saveEntityInDB() {
        var savedDTO = psService.addProductSeries(createValidPSDTO());
        assertThat(psRepository.findById(savedDTO.getId()).orElse(null)).isNotNull();
    }

    @Test
    public void saveEntity_whenImageUrlIsNotSet_savePSWithDefaultImageUrl() {
        var psWithNullUrl = TeaProductSeriesDTO.builder()
                .description("description")
                .name("name")
                .prices("20-30")
                .isTeaBag(true)
                .build();
        var savedPs = psService.addProductSeries(psWithNullUrl);
        assertThat(savedPs.getImageUrl()).isNotNull();

    }

    @Test
    public void updateEntity_whenIdIsFoundInDB_updateDTOInDB() {
        var psEntity = uploadPsAndTea();
        var dto = createValidPSDTO();

        dto.setName(newName);
        psService.updateById(psEntity.getId(), dto);

        var byId = psRepository.findById(psEntity.getId()).orElse(null);
        assertThat(byId).isNotNull();
        assertThat(byId.getName()).isEqualTo(newName);
    }

    @Test
    public void updateEntity_whenHaveChildren_receiveUpdatedPsWithTeaSet() {
        var psEntity = uploadPsAndTea();

        var psDTO = createValidPSDTO();
        psDTO.setName(newName);
        psService.updateById(psEntity.getId(), psDTO);

        var psByID = psService.findById(psEntity.getId());
        assertThat(psByID.getTeaList()).isNotNull();
    }

    @Test
    public void updateEntity_whenUpdateIsSuccessful_receiveString() {
        var psEntity = uploadPSEntity();

        var dto = createValidPSDTO();
        dto.setName(newName);

        assertThat(psService
                .updateById(psEntity.getId(), dto))
                .isNotNull();
    }

    @Test
    public void updateEntity_whenEntityIdIsNotFoundInDB_throwResourceNotFoundException() {
        assertThrows(
                ResourceNotFoundException.class,
                () -> psService.updateById(100, createValidPSDTO()));
    }

    @Test
    public void getDTOById_whenDTOHasChildren_receiveDTOWithChildren() {
        var psEntity = uploadPsAndTea();

        var psByID = psService.findById(psEntity.getId());
        assertThat(psByID.getTeaList()).isNotNull();
    }

    @Test
    public void getDTOById_whenIdIsFoundInDB_receiveDTO() {
        var savedDTO = psService.addProductSeries(createValidPSDTO());
        assertThat(psService.findById(savedDTO.getId())).isNotNull();
    }

    @Test
    public void getDTOById_whenIdIsNotFoundInDB_throwResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class,
                () -> psService.findById(100));
    }

    @Test
    public void deleteEntity_whenHaveChildren_deleteChildrenAlso() {
        var psEntity = uploadPsAndTea();
        assertThat(psRepository.findAll().size()).isEqualTo(1);

        psService.deleteById(psEntity.getId());
        entityManager.flush();

        assertThat(teaRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void deleteEntity_whenIdIsFoundInDB_entityIsDeletedFromDB() {
        var psEntity = uploadPSEntity();
        psService.deleteById(psEntity.getId());
        assertThat(psRepository.findById(psEntity.getId()).orElse(null)).isNull();
    }

    @Test
    public void deleteEntity_whenIdIsNotFoundInDB_throwResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class,
                () -> psService.deleteById(100));
    }

    @Test
    public void getAllFromDB_whenThereAreTwoEntities_receiveASetWithTwoDTOs() {
        uploadPSEntity();
        uploadPSEntity();
        assertThat(psRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void getAllFromDB_whenThereAreNoEntities_receiveAnEmptySet() {
        List<TeaProductSeriesDTO> allPS = psService.getAll();
        assertThat(allPS.size()).isEqualTo(0);
    }

    @Test
    public void getAllFromDB_whenThereAreChildren_receiveAllChildren() {
        uploadPSEntity();
        uploadPSEntity();
        List<List<TeaDTO>> allTeaList =
                psService.getAll().stream().map(
                TeaProductSeriesDTO::getTeaList).toList();
        assertThat(allTeaList.size()).isEqualTo(2);
    }


}
