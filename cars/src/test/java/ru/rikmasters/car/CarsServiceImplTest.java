package ru.rikmasters.car;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rikmasters.car.dto.CarCreateDTO;
import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.car.dto.CarShortDto;
import ru.rikmasters.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
@Transactional
class CarsServiceImplTest {
    private final CarRepository carRepository;
    private final CarsService carsService;
    private Car car;
    CarCreateDTO carCreateDTO;

    @BeforeEach
    void start() {
        car = new Car(1L, "vin", "sadf", "kl", "q3r",
                LocalDate.now(), new ArrayList<>(), 1L);
        carCreateDTO = new CarCreateDTO(1L, "num", "denom", "asfd", "sfdg",
                LocalDate.now(), new ArrayList<>());
    }

    @AfterEach
    void clean() {
        carRepository.deleteAll();

    }

    @Test
    void create() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        assertEquals(carCreateDTO1.getManufacturer(), carCreateDTO.getManufacturer());
        assertEquals(carCreateDTO1.getVin(), carCreateDTO.getVin());
        assertEquals(carCreateDTO1.getBrand(), carCreateDTO.getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carCreateDTO.getGovernmentNumber());
        assertEquals(carCreateDTO1.getYearOfIssue(), carCreateDTO.getYearOfIssue());
    }

    @Test
    void get() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        CarDTO carDTO1 = carsService.getCar(carCreateDTO1.getId());

        assertEquals(carCreateDTO1.getManufacturer(), carDTO1.getManufacturer());
        assertEquals(carCreateDTO1.getVin(), carDTO1.getVin());
        assertEquals(carCreateDTO1.getBrand(), carDTO1.getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carDTO1.getGovernmentNumber());
        assertEquals(carCreateDTO1.getId(), carDTO1.getId());
        assertEquals(carCreateDTO1.getYearOfIssue().toString(), carDTO1.getYearOfIssue());
    }

    @Test
    void remove() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        carsService.removeCar(carCreateDTO1.getId());

        List<Car> cars = carRepository.findAll();
        assertTrue(cars.isEmpty());
    }

    @Test
    void patch() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        CarDTO carDTO1 = carsService.getCar(carCreateDTO1.getId());

        assertEquals(carCreateDTO1.getManufacturer(), carDTO1.getManufacturer());
        assertEquals(carCreateDTO1.getVin(), carDTO1.getVin());
        assertEquals(carCreateDTO1.getBrand(), carDTO1.getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carDTO1.getGovernmentNumber());
        assertEquals(carCreateDTO1.getId(), carDTO1.getId());
        assertEquals(carCreateDTO1.getYearOfIssue().toString(), carDTO1.getYearOfIssue());

        CarDTO patch = carsService.getCar(carDTO1.getId());
        patch.setManufacturer("sadfgvbghj");
        CarDTO carDTO2 = carsService.patch(patch, carDTO1.getId());

        assertEquals(patch.getManufacturer(), carDTO2.getManufacturer());
        assertNotEquals(carCreateDTO1.getManufacturer(), carDTO2.getManufacturer());

        assertEquals(carCreateDTO1.getVin(), carDTO2.getVin());
        assertEquals(carCreateDTO1.getBrand(), carDTO2.getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carDTO2.getGovernmentNumber());
        assertEquals(carCreateDTO1.getId(), carDTO2.getId());
        assertEquals(carCreateDTO1.getYearOfIssue().toString(), carDTO2.getYearOfIssue());
    }

    @Test
    void getAllCar() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        List<CarShortDto> carDTO1 = carsService.getAllCars(0, 10);

        assertEquals(carCreateDTO1.getManufacturer(), carDTO1.get(0).getManufacturer());
        assertEquals(carCreateDTO1.getVin(), carDTO1.get(0).getVin());
        assertEquals(carCreateDTO1.getBrand(), carDTO1.get(0).getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carDTO1.get(0).getGovernmentNumber());
        assertEquals(carCreateDTO1.getId(), carDTO1.get(0).getId());
        assertEquals(carCreateDTO1.getYearOfIssue().toString(), carDTO1.get(0).getYearOfIssue());
    }

    @Test
    void search() {
        CarCreateDTO carCreateDTO1 = carsService.create(carCreateDTO);

        List<CarShortDto> carDTO1 = carsService.searchCars("2015-10-05", "2030-05-10", 0, 10);

        assertEquals(carCreateDTO1.getManufacturer(), carDTO1.get(0).getManufacturer());
        assertEquals(carCreateDTO1.getVin(), carDTO1.get(0).getVin());
        assertEquals(carCreateDTO1.getBrand(), carDTO1.get(0).getBrand());
        assertEquals(carCreateDTO1.getGovernmentNumber(), carDTO1.get(0).getGovernmentNumber());
        assertEquals(carCreateDTO1.getId(), carDTO1.get(0).getId());
        assertEquals(carCreateDTO1.getYearOfIssue().toString(), carDTO1.get(0).getYearOfIssue());
    }

    @Test
    void removeOwner() {
        Long owner = 1L;
        car.setOwner(1L);

        Car car1 = carRepository.save(car);
        CarDTO carDTO1 = carsService.getCar(car1.getId());

        assertEquals(owner, carDTO1.getOwner());

        carsService.removeOwnerCar(carDTO1.getId());
        CarDTO result = carsService.getCar(carDTO1.getId());

        assertNotEquals(owner, result.getOwner());
    }

    @Test
    void removeOwnerCarExcep() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            carsService.removeOwnerCar(20000L);
        });

        Assertions.assertEquals("Такой автомобиль не существует.", thrown.getMessage());
    }

    @Test
    void patchOwnerCarExcep() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            carsService.patchOwnerCar(20000L, 1000L);
        });

        Assertions.assertEquals("Такой автомобиль не существует.", thrown.getMessage());
    }

    @Test
    void setOwnerExcep() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            carsService.setOwner(20000L, 1000L);
        });

        Assertions.assertEquals("Такой автомобиль не существует.", thrown.getMessage());
    }
}
