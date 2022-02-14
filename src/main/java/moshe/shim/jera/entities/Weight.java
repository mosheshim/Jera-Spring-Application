package moshe.shim.jera.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer weight;

    private Integer price;


}
