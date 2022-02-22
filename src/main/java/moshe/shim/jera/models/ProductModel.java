package moshe.shim.jera.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class ProductModel {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    @Column(name = "upload_date", nullable = false)
    protected Date uploadDate;

    @NotNull
    protected Integer price;

    @NotNull
    protected String name;

    @Column(name = "image_url")
    @NotNull
    protected String imageUrl;

    @NotNull
    protected String description;

    @Column(name = "in_stock")
    @NotNull
    @Builder.Default
    protected boolean inStock = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductModel that = (ProductModel) o;

        if (id == that.id) return true;
        if (inStock != that.inStock) return false;
        if (!price.equals(that.price)) return false;
        if (!name.equals(that.name)) return false;
        if (!imageUrl.equals(that.imageUrl)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + price.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (inStock ? 1 : 0);
        return result;
    }
}


