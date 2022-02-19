package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ValueGenerationType;
import org.hibernate.validator.constraints.Range;

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
public class Product{

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    protected Date uploadDate;

    protected int price;

    @NotEmpty
    protected String name;

    @NotEmpty
    @Builder.Default
    protected String imageUrl ="default url";

    protected boolean inStock;

    @NotEmpty
    protected String description;

}


