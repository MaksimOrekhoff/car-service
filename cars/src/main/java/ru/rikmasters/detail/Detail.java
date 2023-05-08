package ru.rikmasters.detail;

import jakarta.persistence.*;
import lombok.*;
import ru.rikmasters.car.Car;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "details", schema = "public")
public class Detail {
    @Id
    @Column(name = "detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "denomination")
    private String denomination;
    @ManyToOne(targetEntity = Car.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", denomination='" + denomination + '\'' +
                ", car=" + car +
                '}';
    }
}
