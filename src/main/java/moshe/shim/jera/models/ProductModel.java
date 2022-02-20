package moshe.shim.jera.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class ProductModel {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    @Column(name = "upload_date", nullable = false)
    protected Date uploadDate;

    protected int price;

    @NotEmpty
    protected String name;

    @Builder.Default
    @Column(name = "image_url")
    protected String imageUrl ="default url";

    @Column(name = "in_stock")
    protected boolean inStock;

    @NotEmpty
    protected String description;

}


