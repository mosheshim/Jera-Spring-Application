package moshe.shim.jera.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    protected Integer price;

    @Column(nullable = false)
    protected String name;

    @Column(name = "image_url", nullable = false)
    protected String imageUrl;

    @Column(nullable = false)
    protected String description;

    @Column(name = "in_stock", nullable = false)
    protected boolean inStock = false;

}


