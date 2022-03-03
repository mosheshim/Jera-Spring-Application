package moshe.shim.jera.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ProductDTOModel {

    @JsonIgnore
    protected Long id;

    @JsonIgnore
    protected Date uploadDate;

    protected Integer price;

    @NotNull
    protected String name;

    @Builder.Default
    protected String imageUrl = "default url";

    protected boolean inStock;

    @NotNull
    protected String description;

}
