package ru.rikmasters.car;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.utils.MyPageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Transactional
class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @Test
    void find() {
        Car car1 = new Car(1L, "23453", "wefe", "sfgf", "sdfg",
                LocalDate.of(2017, 10, 5), new ArrayList<>(), 1L);
        Car car2 = new Car(2L, "234sdfg53", "wesdffe", "sfsdfgf", "sdfsdfg",
                LocalDate.of(2021, 10, 5), new ArrayList<>(), 2L);

        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> result = carRepository.findAllByYearOfIssueAfterAndYearOfIssueBefore(
                LocalDate.of(2016, 10, 5), LocalDate.of(2020, 5, 6),
                new MyPageRequest(0, 10, Sort.unsorted()));

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(car1.getId(), result.get(0).getId());
        assertEquals(car1.getOwner(), result.get(0).getOwner());
        assertEquals(car1.getBrand(), result.get(0).getBrand());
        assertEquals(car1.getGovernmentNumber(), result.get(0).getGovernmentNumber());
        assertEquals(car1.getManufacturer(), result.get(0).getManufacturer());
        assertEquals(car1.getVin(), result.get(0).getVin());
    }


}