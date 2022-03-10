package moshe.shim.jera.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@AllArgsConstructor()
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoffeeDTO extends ProductDTO {

    @NotEmpty
    private String countryOfOrigin;

    @NotNull
    @Range(min = 1, max = 3)
    private Integer roastingLevel;

    @NotEmpty
    private String tasteProfile;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer bitterness;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer sweetness;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer acidity;

    @NotNull
    @Range(min = 0, max = 5)
    private Integer body;
}
