package ru.rikmasters.car;

import jakarta.persistence.*;
import lombok.*;
import ru.rikmasters.detail.Detail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "cars", schema = "public")
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin")
    private String vin;

    @Column(name = "government_number")
    private String governmentNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year_of_issue")
    private LocalDate yearOfIssue;


    @OneToMany(targetEntity = Detail.class, mappedBy = "car",
            fetch = FetchType.EAGER)
    private List<Detail> details = new ArrayList<>();

    @Column(name = "owner_id")
    private Long owner;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", governmentNumber='" + governmentNumber + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", brand='" + brand + '\'' +
                ", yearOfIssue='" + yearOfIssue + '\'' +
                '}';
    }
}
