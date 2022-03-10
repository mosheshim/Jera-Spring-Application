package moshe.shim.jera.entities;

import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "Tea_product_series")
@Entity
public class TeaProductSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @CreationTimestamp
    @Column(name = "upload_date", nullable = false, updatable = false)
    protected Date uploadDate;

    @NotNull
    private String description;

    @NotNull
    private String prices;

    @NotNull
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "is_tea_bag")
    private Boolean isTeaBag;


    @OneToMany(mappedBy = "teaProductSeries", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "tea_list")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Tea> teaSet;

}
