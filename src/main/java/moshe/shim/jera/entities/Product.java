package moshe.shim.jera.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import moshe.shim.jera.entities.CartItem;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    @CreationTimestamp
    @Column(name = "upload_date", nullable = false, updatable = false)
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CartItem> cartItems;

}


