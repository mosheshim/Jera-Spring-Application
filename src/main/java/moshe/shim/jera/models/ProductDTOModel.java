package moshe.shim.jera.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public abstract class ProductDTOModel {
    protected Long id;

    protected Date uploadDate;

    protected int price;

    @NotEmpty
    protected String name;

    @Builder.Default
    protected String imageUrl = "default url";

    protected boolean inStock;

    @NotEmpty
    protected String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDTOModel that = (ProductDTOModel) o;

        if (id.equals(that.id)) return true;
        if (price != that.price) return false;
        if (inStock != that.inStock) return false;
        if (!name.equals(that.name)) return false;
        if (!imageUrl.equals(that.imageUrl)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + price;
        result = 31 * result + name.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + (inStock ? 1 : 0);
        result = 31 * result + description.hashCode();
        return result;
    }
}
