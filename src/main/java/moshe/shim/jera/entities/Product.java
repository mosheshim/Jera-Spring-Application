package moshe.shim.jera.entities;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product{

    @JsonSerialize
    @Id()
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @JsonView
    private Date uploadDate;

    @NotNull
    @JsonProperty
    private int price;

    @NotNull
    @NotEmpty
    @JsonProperty
    private String name;

    @NotNull
    @JsonProperty
    private String imageUrl = "Default url";

    @NotNull
    @JsonProperty
    private Boolean inStock;

    @NotNull
    @JsonProperty
    private String description;

    public Product(int price, String name, String imageUrl, Boolean inStock, String description) {
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
        this.inStock = inStock;
        this.description = description;
    }
}


