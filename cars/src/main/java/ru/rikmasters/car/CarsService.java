package ru.rikmasters.car;

import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.car.dto.CarShortDto;
import ru.rikmasters.car.dto.CarCreateDTO;

import java.util.List;

public interface CarsService {
    CarCreateDTO create(CarCreateDTO carDTO);

    CarDTO getCar(Long carId);

    void removeCar(Long carId);

    CarDTO patch(CarDTO carDTO, Long carId);

    List<CarShortDto> getAllCars(Integer from, Integer size);

    List<CarShortDto> searchCars(String start, String end, Integer from, Integer size);

    void removeOwnerCar(Long carId);

    void patchOwnerCar(Long ownerId, Long carId);

    void setOwner(Long ownerId, Long carId);
}
