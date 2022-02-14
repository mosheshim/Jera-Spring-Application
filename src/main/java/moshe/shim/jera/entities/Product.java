package moshe.shim.jera.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product{

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    protected Date uploadDate;

    @NotNull
    protected int price;

    @NotNull
    @NotEmpty
    protected String name;

    @NotNull
    protected String imageUrl;

    @NotNull
    protected Boolean inStock;

    @NotNull
    protected String description;

}


