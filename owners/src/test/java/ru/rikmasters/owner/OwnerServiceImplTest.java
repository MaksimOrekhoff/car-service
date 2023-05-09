package ru.rikmasters.owner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;
import ru.rikmasters.utils.DriversLicense;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
@Transactional
class OwnerServiceImplTest {
    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;
    Owner owner;
    OwnerCreateDTO ownerCreateDTO;

    @BeforeEach
    void start() {
        owner = new Owner(1L, "a", "b", "c", "d",
                DriversLicense.D, "2000-10-5", 5, 2L);
        ownerCreateDTO = new OwnerCreateDTO(1L, "a", "b", "c", "d",
                "D", "2000-10-5", 5);
    }

    @AfterEach
    void clean() {
        ownerRepository.deleteAll();
    }

    @Test
    public void createOwner() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);

        assertEquals(ownerCreateDTO.getDateOfBirth(), ownerCreateDTO1.getDateOfBirth());
        assertEquals(ownerCreateDTO.getPassport(), ownerCreateDTO1.getPassport());
        assertEquals(ownerCreateDTO.getDriversLicense(), ownerCreateDTO1.getDriversLicense());
        assertEquals(ownerCreateDTO.getFirstName(), ownerCreateDTO1.getFirstName());
        assertEquals(ownerCreateDTO.getMiddleName(), ownerCreateDTO1.getMiddleName());
        assertEquals(ownerCreateDTO.getLastName(), ownerCreateDTO1.getLastName());
    }

    @Test
    public void setNewOwner() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);
        Long car = 2L;
        ownerService.setNewOwner(ownerCreateDTO1.getId(), car);

        OwnerDTO ownerDTO = ownerService.getOwner(ownerCreateDTO1.getId());

        assertEquals(car, ownerDTO.getCar());
        assertEquals(ownerCreateDTO.getDateOfBirth(), ownerDTO.getDateOfBirth());
        assertEquals(ownerCreateDTO.getPassport(), ownerDTO.getPassport());
        assertEquals(ownerCreateDTO.getDriversLicense(), ownerDTO.getDriversLicense());
        assertEquals(ownerCreateDTO.getFirstName(), ownerDTO.getFirstName());
        assertEquals(ownerCreateDTO.getMiddleName(), ownerDTO.getMiddleName());
        assertEquals(ownerCreateDTO.getLastName(), ownerDTO.getLastName());
    }

    @Test
    public void getOwner() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);

        OwnerDTO ownerDTO = ownerService.getOwner(ownerCreateDTO1.getId());

        assertEquals(ownerCreateDTO.getDateOfBirth(), ownerDTO.getDateOfBirth());
        assertEquals(ownerCreateDTO.getPassport(), ownerDTO.getPassport());
        assertEquals(ownerCreateDTO.getDriversLicense(), ownerDTO.getDriversLicense());
        assertEquals(ownerCreateDTO.getFirstName(), ownerDTO.getFirstName());
        assertEquals(ownerCreateDTO.getMiddleName(), ownerDTO.getMiddleName());
        assertEquals(ownerCreateDTO.getLastName(), ownerDTO.getLastName());
    }

    @Test
    public void deleteOwner() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);

        ownerService.removeOwner(ownerCreateDTO1.getId());
        List<Owner> owners = ownerRepository.findAll();

        assertTrue(owners.isEmpty());
    }

    @Test
    public void patchOwner() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);

        OwnerDTO ownerDTO = ownerService.getOwner(ownerCreateDTO1.getId());

        assertEquals(ownerCreateDTO.getDateOfBirth(), ownerDTO.getDateOfBirth());
        assertEquals(ownerCreateDTO.getPassport(), ownerDTO.getPassport());
        assertEquals(ownerCreateDTO.getDriversLicense(), ownerDTO.getDriversLicense());
        assertEquals(ownerCreateDTO.getFirstName(), ownerDTO.getFirstName());
        assertEquals(ownerCreateDTO.getMiddleName(), ownerDTO.getMiddleName());
        assertEquals(ownerCreateDTO.getLastName(), ownerDTO.getLastName());

        OwnerDTO ownerDTO1 = ownerService.getOwner(ownerCreateDTO1.getId());
        ownerDTO1.setExperience(ownerDTO.getExperience() + 10);
        OwnerDTO result = ownerService.patchOwner(ownerDTO1, ownerDTO.getId());

        assertNotEquals(result.getExperience(), ownerDTO.getExperience());
        assertEquals(result.getId(), ownerDTO.getId());
    }

    @Test
    public void deleteOwnerException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Owner owner1 = ownerRepository.save(owner);
            ownerService.removeOwner(owner1.getId() + 1);
        });

        Assertions.assertEquals("Такой автовладелец не существует.", thrown.getMessage());
    }

    @Test
    public void deleteAutoException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Owner owner1 = ownerRepository.save(owner);
            ownerService.deleteAuto(owner1.getId() + 1);
        });

        Assertions.assertEquals("Такой автовладелец не существует.", thrown.getMessage());
    }

    @Test
    public void setNewOwnerException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Owner owner1 = ownerRepository.save(owner);
            ownerService.setNewOwner(owner1.getId() + 1, 1L);
        });

        Assertions.assertEquals("Такой автовладелец не существует.", thrown.getMessage());
    }

    @Test
    public void setOwnerException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Owner owner1 = ownerRepository.save(owner);
            ownerService.setOwner(owner1.getId() + 1, 1L);
        });

        Assertions.assertEquals("Такой автовладелец не существует.", thrown.getMessage());
    }

    @Test
    public void getAllOwners() {
        OwnerCreateDTO ownerCreateDTO1 = ownerService.create(ownerCreateDTO);

        List<OwnerDTO> ownerDTOS = ownerService.getAllOwnersAndSort(0, 10);

        assertEquals(ownerCreateDTO.getDateOfBirth(), ownerDTOS.get(0).getDateOfBirth());
        assertEquals(ownerCreateDTO.getPassport(), ownerDTOS.get(0).getPassport());
        assertEquals(ownerCreateDTO.getDriversLicense(), ownerDTOS.get(0).getDriversLicense());
        assertEquals(ownerCreateDTO.getFirstName(), ownerDTOS.get(0).getFirstName());
        assertEquals(ownerCreateDTO.getMiddleName(), ownerDTOS.get(0).getMiddleName());
        assertEquals(ownerCreateDTO.getLastName(), ownerDTOS.get(0).getLastName());
        assertEquals(ownerCreateDTO1.getId(), ownerDTOS.get(0).getId());
    }
}