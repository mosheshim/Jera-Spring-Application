package moshe.shim.jera.payload;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class WeightDTO {

    @NotNull
    private Integer weight;

    @NotNull
    private Integer price;

    @NotNull
    private Boolean inStock;
}
