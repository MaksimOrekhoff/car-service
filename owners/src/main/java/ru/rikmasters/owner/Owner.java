package ru.rikmasters.owner;

import jakarta.persistence.*;
import lombok.*;
import ru.rikmasters.utils.DriversLicense;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "owners", schema = "public")
public class Owner {
    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "passport")
    private String passport;

    @Enumerated(EnumType.STRING)
    @Column(name = "drivers_license")
    private DriversLicense driversLicense;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "car_id")
    private Long car;

}
