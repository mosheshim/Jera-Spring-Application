package moshe.shim.jera.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Setter
@ToString
public class ProductDTOModel {
    protected long id;

    protected Date uploadDate;

    protected int price;

    @NotEmpty
    protected String name;

    @NotNull
    protected String imageUrl;

    protected boolean inStock;

    @NotEmpty
    protected String description;


}
