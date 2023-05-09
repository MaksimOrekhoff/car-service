package ru.rikmasters.detail;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rikmasters.car.Car;
import ru.rikmasters.car.CarRepository;
import ru.rikmasters.detail.dto.DetailDTO;
import ru.rikmasters.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
@Transactional
class DetailServiceImplTest {
    private final DetailService detailService;
    private final DetailRepository detailRepository;
    private final CarRepository carRepository;
    private Car car;
    private DetailDTO detailDTO;

    @BeforeEach
    void start() {
        car = new Car(1L, "vin", "sadf", "kl", "q3r",
                LocalDate.now(), new ArrayList<>(), 1L);
        detailDTO = new DetailDTO(1L, "1", "sfdg", 1L);
    }

    @AfterEach
    void clean() {
        detailRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    void install() {
        Car car1 = carRepository.save(car);
        detailDTO.setCar(car1.getId());

        DetailDTO dto = detailService.installation(detailDTO);

        assertEquals(dto.getCar(), car1.getId());
        assertEquals(dto.getDenomination(), detailDTO.getDenomination());
        assertEquals(dto.getSerialNumber(), detailDTO.getSerialNumber());
    }

    @Test
    void remove() {
        Car car1 = carRepository.save(car);
        detailDTO.setCar(car1.getId());
        DetailDTO dto = detailService.installation(detailDTO);

        assertEquals(dto.getDenomination(), detailDTO.getDenomination());
        assertEquals(dto.getSerialNumber(), detailDTO.getSerialNumber());

        detailService.removeDetail(dto.getId());
        List<Detail> details = detailRepository.findAll();

        assertTrue(details.isEmpty());
    }

    @Test
    void removeExcep() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            detailService.installation(detailDTO);
        });

        Assertions.assertEquals("Такой автомобиль не существует.", thrown.getMessage());
    }

    @Test
    void getDetail() {
        Car car1 = carRepository.save(car);
        detailDTO.setCar(car1.getId());

        DetailDTO dto = detailService.installation(detailDTO);
        DetailDTO res = detailService.getDetail(dto.getId()).get(0);

        assertEquals(dto.getCar(), car1.getId());
        assertEquals(dto.getDenomination(), res.getDenomination());
        assertEquals(dto.getSerialNumber(), res.getSerialNumber());
        assertEquals(dto.getCar(), res.getCar());
    }


    @Test
    void getAllDetail() {
        Car car1 = carRepository.save(car);
        detailDTO.setCar(car1.getId());

        DetailDTO dto = detailService.installation(detailDTO);
        List<DetailDTO> res = detailService.getDetail(dto.getId());

        assertEquals(dto.getCar(), car1.getId());
        assertEquals(dto.getDenomination(), res.get(0).getDenomination());
        assertEquals(dto.getSerialNumber(), res.get(0).getSerialNumber());
        assertEquals(dto.getCar(), res.get(0).getCar());
    }

    @Test
    void searchDetail() {
        Car car1 = carRepository.save(car);
        detailDTO.setCar(car1.getId());

        DetailDTO dto = detailService.installation(detailDTO);
        List<DetailDTO> res = detailService.searchDetails(detailDTO.getDenomination(), 0, 10);

        assertEquals(dto.getCar(), car1.getId());
        assertEquals(dto.getDenomination(), res.get(0).getDenomination());
        assertEquals(dto.getSerialNumber(), res.get(0).getSerialNumber());
        assertEquals(dto.getCar(), res.get(0).getCar());
    }
}