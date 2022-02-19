package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.entities.Tea;
import moshe.shim.jera.entities.TeaProductSeries;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeaDTO extends ProductDTO{
//    @JsonInclude
    private List<Weight> weights;


}
