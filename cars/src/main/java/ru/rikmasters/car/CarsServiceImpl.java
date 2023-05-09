package ru.rikmasters.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.car.dto.CarShortDto;
import ru.rikmasters.car.dto.CarCreateDTO;
import ru.rikmasters.detail.Detail;
import ru.rikmasters.detail.DetailMapper;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.owner.OwnerClient;
import ru.rikmasters.utils.MyPageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarsServiceImpl implements CarsService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final DetailMapper detailMapper;
    private final OwnerClient ownerClient;

    @Override
    public CarCreateDTO create(CarCreateDTO carDTO) {
        if (!carDTO.getDetails().isEmpty()) {
            Car car = carRepository.save(carMapper.toCreateCar(carDTO, detailMapper.toDetail(carDTO.getDetails(), null)));
            return carMapper.toCarCreateDTO(car, detailMapper.toDetailDTO(car.getDetails()));
        }
        Car car = carRepository.save(carMapper.toCreateCar(carDTO, new ArrayList<>()));
        log.debug("Добавлен авто {} ", car);
        return carMapper.toCarCreateDTO(car, new ArrayList<>());
    }

    @Override
    public CarDTO getCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        log.debug("Получен auto {} ", car);
        if (!car.getDetails().isEmpty()) {
            return carMapper.toCarDTO(car, detailMapper.toDetailDTO(car.getDetails()));
        }
        return carMapper.toCarDTO(car, new ArrayList<>());
    }

    @Override
    public void removeCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        carRepository.deleteById(carId);
        if (car.getOwner() != null) {
            ownerClient.deleteAuto(car.getOwner());
        }
        log.info("Удален автомобиль: {}", car);
    }

    @Override
    public CarDTO patch(CarDTO carDTO, Long carId) {
        Car oldCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        Car car = carMapper.toPatchCar(carDTO, oldCar);
        if (carDTO.getOwner() != null) {
            ownerClient.changeOwner(carDTO.getOwner(), carId);
        }
        setDetails(carDTO, oldCar, car);
        Car newCar = carRepository.save(car);
        log.debug("Изменен автомобиль: {}", newCar);
        if (car.getDetails() == null) {
            return carMapper.toCarDTO(newCar, new ArrayList<>());
        }
        return carMapper.toCarDTO(newCar, detailMapper.toDetailDTO(car.getDetails()));
    }

    @Override
    public List<CarShortDto> getAllCars(Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("vin"));
        Page<Car> cars = carRepository.findAll(myPageRequest);
        log.debug("Получены все автомобили: {} и отсортированы по vin", cars);
        return cars.stream()
                .map(this::toCarShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarShortDto> searchCars(String start, String end, Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("vin"));
        List<Car> cars = carRepository
                .findAllByYearOfIssueAfterAndYearOfIssueBefore(LocalDate.parse(start), LocalDate.parse(end), myPageRequest);
        log.debug("Получены все автомобили: {} и отсортированы по vin", cars);
        return cars.stream()
                .map(this::toCarShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeOwnerCar(Long carId) {
        Car oldCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        oldCar.setOwner(null);
        carRepository.save(oldCar);
        log.debug("Удален владелец автомобиля: {}", oldCar);
    }

    @Override
    public void patchOwnerCar(Long ownerId, Long carId) {
        Car oldCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        oldCar.setOwner(ownerId);
        carRepository.save(oldCar);
        log.debug("У автомобиля: {} новый валделец {}", oldCar, ownerId);
    }

    @Override
    public void setOwner(Long ownerId, Long carId) {
        Car oldCar = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        oldCar.setOwner(ownerId);
        carRepository.save(oldCar);
        ownerClient.setOwner(ownerId, carId);
        log.debug("У автомобиля: {} новый валделец {}", oldCar, ownerId);
    }

    private void setDetails(CarDTO carDTO, Car oldCar, Car car) {
        if (!oldCar.getDetails().isEmpty()) {
            car.setDetails(oldCar.getDetails());
        }
        if (!carDTO.getDetails().isEmpty()) {
            List<Detail> details = detailMapper.toDetail(carDTO.getDetails(), car);
            for (Detail d : details) {
                car.getDetails().add(d);
            }
        }
    }

    public CarShortDto toCarShortDto(Car car) {
        return CarShortDto.builder()
                .id(car.getId())
                .brand(car.getBrand() != null ? car.getBrand() : null)
                .governmentNumber(car.getGovernmentNumber() != null ? car.getGovernmentNumber() : null)
                .manufacturer(car.getManufacturer() != null ? car.getManufacturer() : null)
                .vin(car.getVin())
                .yearOfIssue(car.getYearOfIssue() != null ? car.getYearOfIssue().toString() : null)
                .owner(car.getOwner() != null ? car.getOwner() : null)
                .build();
    }
}
