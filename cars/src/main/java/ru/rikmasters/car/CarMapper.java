package ru.rikmasters.car;

import org.springframework.stereotype.Component;
import ru.rikmasters.car.dto.CarCreateDTO;
import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.detail.Detail;
import ru.rikmasters.detail.dto.DetailDTO;

import java.time.LocalDate;
import java.util.List;

@Component
public class CarMapper {

    public Car toCreateCar(CarCreateDTO carDTO, List<Detail> details) {
        return Car.builder()
                .id(carDTO.getId())
                .vin(carDTO.getVin())
                .governmentNumber(carDTO.getGovernmentNumber())
                .brand(carDTO.getBrand())
                .manufacturer(carDTO.getManufacturer())
                .yearOfIssue(carDTO.getYearOfIssue())
                .details(details)
                .owner(null)
                .build();
    }

    public Car toCar(CarDTO carDTO, List<Detail> detail) {
        return Car.builder()
                .id(carDTO.getId())
                .vin(carDTO.getVin())
                .governmentNumber(carDTO.getGovernmentNumber())
                .brand(carDTO.getBrand())
                .manufacturer(carDTO.getManufacturer())
                .yearOfIssue(LocalDate.parse(carDTO.getYearOfIssue()))
                .details(detail)
                .owner(carDTO.getOwner())
                .build();
    }

    public CarCreateDTO toCarCreateDTO(Car car, List<DetailDTO> details) {
        return CarCreateDTO.builder()
                .id(car.getId())
                .vin(car.getVin())
                .governmentNumber(car.getGovernmentNumber())
                .brand(car.getBrand())
                .manufacturer(car.getManufacturer())
                .yearOfIssue(car.getYearOfIssue())
                .details(details)
                .build();
    }

    public CarDTO toCarDTO(Car car, List<DetailDTO> detailDTO) {
        return CarDTO.builder()
                .id(car.getId())
                .vin(car.getVin())
                .governmentNumber(car.getGovernmentNumber())
                .brand(car.getBrand())
                .manufacturer(car.getManufacturer())
                .yearOfIssue(car.getYearOfIssue().toString())
                .details(detailDTO)
                .owner(car.getOwner())
                .build();
    }

    public Car toPatchCar(CarDTO carDTO, Car car) {
        return Car.builder()
                .id(car.getId())
                .governmentNumber(carDTO.getGovernmentNumber() != null ?
                        carDTO.getGovernmentNumber() : car.getGovernmentNumber() != null ?
                        car.getGovernmentNumber() : null)
                .yearOfIssue(carDTO.getYearOfIssue() != null ?
                        LocalDate.parse(carDTO.getYearOfIssue()) : car.getYearOfIssue() != null ?
                        car.getYearOfIssue() : null)
                .manufacturer(carDTO.getManufacturer() != null ?
                        carDTO.getManufacturer() : car.getManufacturer() != null ?
                        car.getManufacturer() : null)
                .brand(carDTO.getBrand() != null ?
                        carDTO.getBrand() : car.getBrand() != null ?
                        car.getBrand() : null)
                .vin(carDTO.getVin() != null ?
                        carDTO.getVin() : car.getVin() != null ?
                        car.getVin() : null)
                .owner(carDTO.getOwner() != null ?
                        carDTO.getOwner() : car.getOwner() != null ?
                        car.getOwner() : null)
                .build();
    }
}
