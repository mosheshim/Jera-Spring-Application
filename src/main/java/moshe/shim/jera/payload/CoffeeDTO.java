package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.entities.Coffee;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoffeeDTO extends ProductDTO{


    @NotEmpty
    private String countryOfOrigin;

    @Range(min = 0, max = 3)
    private Integer roastingLevel;

    @NotEmpty
    private String tasteProfile;

    @Range(min = 0, max = 5)
    private Integer bitterness;

    @Range(min = 0, max = 5)
    private Integer sweetness;

    @Range(min = 0, max = 5)
    private Integer acidity;

    @Range(min = 0, max = 5)
    private Integer body;


}
