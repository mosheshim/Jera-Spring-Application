package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
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
