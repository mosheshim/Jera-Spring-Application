package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.models.ProductDTOModel;
import moshe.shim.jera.models.Weight;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeaDTO extends ProductDTOModel {
//    @JsonInclude
    private List<Weight> weights;


}
