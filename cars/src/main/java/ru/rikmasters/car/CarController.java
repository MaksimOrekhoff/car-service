package ru.rikmasters.car;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.car.dto.CarShortDto;
import ru.rikmasters.car.dto.CarCreateDTO;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/cars")
public class CarController {
    private final CarsService carsService;

    @PostMapping
    public CarCreateDTO createCar(@Validated @RequestBody CarCreateDTO carCreateDTO) {
        log.debug("Получен Post-запрос на добавление автомобиля {}", carCreateDTO);
        return carsService.create(carCreateDTO);
    }

    @PostMapping("/{ownerId}/{carId}")
    public void setCarOwner(@PathVariable @NotBlank Long ownerId,
                            @PathVariable @NotBlank Long carId) {
        log.info("Получен Post-запрос на владение водителем с id: {} автомобилем с id: {}", ownerId, carId);
        carsService.setOwner(ownerId, carId);
    }

    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable @NotBlank Long carId) {
        log.debug("Получен Get-запрос на получение автомобиля с id: {}.", carId);
        return carsService.getCar(carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable @NotBlank Long carId) {
        log.debug("Получен Delete-запрос на удаление auto c id: {}", carId);
        carsService.removeCar(carId);
    }

    @DeleteMapping("/removeCarOwner/{carId}")
    public void deleteOwnerCar(@PathVariable @NotBlank Long carId) {
        log.debug("Получен Delete-запрос на удаление владельца auto  {}", carId);
        carsService.removeOwnerCar(carId);
    }

    @PatchMapping("/{carId}")
    public CarDTO patchCar(@Validated @RequestBody CarDTO carDTO,
                           @PathVariable @NotBlank Long carId) {
        log.debug("Получен Patch-запрос на изменение давнных об автомобиле {}", carDTO);
        return carsService.patch(carDTO, carId);
    }

    @PutMapping("/changeCarOwner/{ownerId}/{carId}")
    public void patchOwnerCar(@PathVariable @NotBlank Long ownerId,
                                @PathVariable @NotBlank Long carId) {
        log.debug("Получен Patch-запрос на изменение владельцем {} автомобил {}", ownerId, carId);
        carsService.patchOwnerCar(ownerId, carId);
    }

    @GetMapping()
    public List<CarShortDto> allCarsAndSortByVIN(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.debug("Получен Get-запрос на получение всех автомобилей c сортировной по VIN");
        return carsService.getAllCars(from, size);
    }

    @GetMapping("/search")
    public List<CarShortDto> searchCarsByParamAndSortByTime(@RequestParam final String start,
                                                            @RequestParam final String end,
                                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.debug("Получен Get-запрос на получение всех автомобилей выпущенных в период с {} по {}", start, end);
        return carsService.searchCars(start, end, from, size);
    }
}
