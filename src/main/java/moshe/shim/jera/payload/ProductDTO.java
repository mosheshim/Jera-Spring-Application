package moshe.shim.jera.payload;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class ProductDTO {
    protected long id;

    protected Date uploadDate;

    protected int price;

    protected String name;

    protected String imageUrl = "Default url";

    protected Boolean inStock;

    protected String description;

}
