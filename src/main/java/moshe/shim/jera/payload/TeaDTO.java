package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
public class TeaDTO extends ProductDTO {

    @Valid
    @Size(min = 1)
    private List<WeightDTO> weights;


}
