package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductDTOModel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
public class TeaDTO extends ProductDTOModel {

    @Valid
    @Size(min = 1)
    private List<WeightDTO> weights;


}
