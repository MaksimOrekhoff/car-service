package ru.rikmasters.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.owner.Owner;
import ru.rikmasters.owner.OwnerRepository;
import ru.rikmasters.utils.DriversLicense;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void findByOwner() {
        Owner owner = new Owner(1L, "a", "b", "c", "d",
                DriversLicense.D, "2000-10-5", 5, 2L);
        Owner owner1 = ownerRepository.save(owner);
        Account account1 = new Account(1L, 100D, 200D, 30D, owner1.getId());

        Account account = accountRepository.save(account1);

        Account result = accountRepository.findByOwnerId(account.getOwnerId())
                .orElseThrow(() -> new NotFoundException("Такой счет не существует."));

        assertEquals(account.getId(), result.getId());
        assertEquals(account.getBlueBalance(), result.getBlueBalance());
        assertEquals(account.getOwnerId(), owner1.getId());
        assertEquals(account.getGreenBalance(), result.getGreenBalance());

    }
}