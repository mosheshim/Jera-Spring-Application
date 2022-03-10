package moshe.shim.jera.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

//    @OneToOne(mappedBy = "address")
//    private User user;

}
