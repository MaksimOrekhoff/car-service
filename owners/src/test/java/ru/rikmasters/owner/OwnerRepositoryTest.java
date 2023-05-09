package ru.rikmasters.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.utils.DriversLicense;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class OwnerRepositoryTest {
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void search() {
        Owner owner1 = new Owner(1L, "a", "b", "c", "d",
                DriversLicense.D, "2000-10-5", 5, 2L);
        Owner owner = ownerRepository.save(owner1);

        MyPageRequest myPageRequest = new MyPageRequest(0, 10, Sort.by("passport"));
        List<Owner> result = ownerRepository.searchOwners("a", myPageRequest);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(owner.getId(), result.get(0).getId());
        assertEquals(owner.getCar(), result.get(0).getCar());
        assertEquals(owner.getExperience(), result.get(0).getExperience());
        assertEquals(owner.getDateOfBirth(), result.get(0).getDateOfBirth());
        assertEquals(owner.getFirstName(), result.get(0).getFirstName());
        assertEquals(owner.getLastName(), result.get(0).getLastName());
    }
}