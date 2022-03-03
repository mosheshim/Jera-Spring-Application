package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductDTOModel;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
public class TeaDTO extends ProductDTOModel {

    @Builder.Default
    private List<WeightDTO> weights = List.of();


}
