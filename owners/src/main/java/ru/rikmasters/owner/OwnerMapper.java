package ru.rikmasters.owner;

import org.springframework.stereotype.Component;
import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;
import ru.rikmasters.utils.DriversLicense;


@Component
public class OwnerMapper {
//    public Owner toOwner(OwnerDTO ownerDTO, List<Car> cars) {
//        return Owner.builder()
//                .id(ownerDTO.getId())
//                .firstName(ownerDTO.getFirstName())
//                .middleName(ownerDTO.getMiddleName())
//                .lastName(ownerDTO.getLastName())
//                .dateOfBirth(ownerDTO.getDateOfBirth())
//                .driversLicense(ownerDTO.getDriversLicense())
//                .passport(ownerDTO.getPassport())
//                .experience(ownerDTO.getExperience())
//                .cars(cars)
//                .build();
//    }

    public Owner toOwnerCreate(OwnerCreateDTO ownerDTO) {
        return Owner.builder()
                .id(ownerDTO.getId())
                .firstName(ownerDTO.getFirstName())
                .middleName(ownerDTO.getMiddleName())
                .lastName(ownerDTO.getLastName())
                .dateOfBirth(ownerDTO.getDateOfBirth())
                .driversLicense(DriversLicense.valueOf(ownerDTO.getDriversLicense()))
                .passport(ownerDTO.getPassport())
                .experience(ownerDTO.getExperience())
                .car(null)
                .build();
    }

    public OwnerCreateDTO toOwnerCreateDTO(Owner owner) {
        return OwnerCreateDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .middleName(owner.getMiddleName())
                .lastName(owner.getLastName())
                .dateOfBirth(owner.getDateOfBirth())
                .driversLicense(owner.getDriversLicense().toString())
                .passport(owner.getPassport())
                .experience(owner.getExperience())
                .build();
    }

    public OwnerDTO toOwnerDTO(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .middleName(owner.getMiddleName())
                .lastName(owner.getLastName())
                .dateOfBirth(owner.getDateOfBirth())
                .driversLicense(owner.getDriversLicense().toString())
                .passport(owner.getPassport())
                .experience(owner.getExperience())
                .car(owner.getCar() != null ? owner.getCar() : null)
                .build();
    }

    public Owner toPatchOwner(OwnerDTO ownerDTO, Owner owner) {
        owner.setFirstName(ownerDTO.getFirstName() != null ?
                ownerDTO.getFirstName() : owner.getFirstName() != null ? owner.getFirstName() : null);
        owner.setMiddleName(ownerDTO.getMiddleName() != null ?
                ownerDTO.getMiddleName() : owner.getMiddleName() != null ? owner.getMiddleName() : null);
        owner.setLastName(ownerDTO.getLastName() != null ?
                ownerDTO.getLastName() : owner.getLastName() != null ? owner.getLastName() : null);
        owner.setDateOfBirth(ownerDTO.getDateOfBirth() != null ?
                ownerDTO.getDateOfBirth() : owner.getDateOfBirth() != null ? owner.getDateOfBirth() : null);
        owner.setDriversLicense(ownerDTO.getDriversLicense() != null ?
                DriversLicense.valueOf(ownerDTO.getDriversLicense()) : owner.getDriversLicense() != null ? owner.getDriversLicense() : null);
        owner.setExperience(ownerDTO.getExperience() != null ?
                ownerDTO.getExperience() : owner.getExperience() != null ? owner.getExperience() : null);
        owner.setPassport(ownerDTO.getPassport() != null ?
                ownerDTO.getPassport() : owner.getPassport() != null ? owner.getPassport() : null);
        owner.setCar(ownerDTO.getCar() != null ?
                ownerDTO.getCar() : owner.getCar() != null ? owner.getCar() : null);
        return owner;
    }
}
