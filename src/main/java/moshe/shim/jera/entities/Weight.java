package moshe.shim.jera.entities;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
@Embeddable
public class Weight {

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private boolean inStock;

}
