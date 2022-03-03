package moshe.shim.jera.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    private String city;

    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    private String zip;

    private String floor;

    private String apartment;

    private String entrance;

    private String phone;

}
