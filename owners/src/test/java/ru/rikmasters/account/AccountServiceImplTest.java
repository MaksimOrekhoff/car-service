package ru.rikmasters.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rikmasters.account.dto.AccountDto;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.owner.Owner;
import ru.rikmasters.owner.OwnerRepository;
import ru.rikmasters.utils.DriversLicense;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
@Transactional
class AccountServiceImplTest {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final OwnerRepository ownerRepository;
    Owner owner;
    Account account;
    AccountDto accountDto;

    @BeforeEach
    void start() {
        owner = new Owner(1L, "a", "b", "c", "d",
                DriversLicense.D, "2000-10-5", 5, 2L);
        account = new Account(1L, 0D, 0D, 0D, null);
        accountDto = new AccountDto(1L, 0D, null);
    }

    @AfterEach
    void clean() {
        ownerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void getException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Owner owner1 = ownerRepository.save(owner);
            accountService.getAccount(owner1.getId() + 1, "RED");
        });

        Assertions.assertEquals("Такой счет не существует.", thrown.getMessage());
    }

    @Test
    public void getAccount() {
        Owner owner1 = ownerRepository.save(owner);
        AccountDto accountDto1 = accountService.create(owner1.getId());

        AccountDto result = accountService.getAccount(owner1.getId(), "RED");

        assertNotNull(result);
        assertEquals(result.getId(), accountDto1.getId());
        assertEquals(result.getOwnerId(), accountDto1.getOwnerId());
        assertEquals(result.getBalance(), accountDto1.getBalance());
    }

    @Test
    public void createAccount() {
        Owner owner1 = ownerRepository.save(owner);

        AccountDto accountDto1 = accountService.create(owner1.getId());

        assertNotNull(accountDto1);
        assertEquals(owner1.getId(), accountDto1.getOwnerId());
        assertEquals(0.0, accountDto1.getBalance());
    }

    @Test
    public void deleteAccount() {
        Owner owner1 = ownerRepository.save(owner);

        AccountDto accountDto1 = accountService.create(owner1.getId());

        assertNotNull(accountDto1);
        assertEquals(owner1.getId(), accountDto1.getOwnerId());
        assertEquals(0.0, accountDto1.getBalance());

        accountService.delete(accountDto1.getId(), owner1.getId());

        List<Account> accounts = accountRepository.findAll();

        assertTrue(accounts.isEmpty());
    }

    @Test
    public void addAccount() {
        Owner owner1 = ownerRepository.save(owner);

        AccountDto accountDto1 = accountService.create(owner1.getId());

        assertNotNull(accountDto1);
        assertEquals(owner1.getId(), accountDto1.getOwnerId());
        assertEquals(0.0, accountDto1.getBalance());
        Double sum = 1000D;
        String text = "RED";
        accountService.add(owner1.getId(), sum, text);

        AccountDto result = accountService.getAccount(owner1.getId(), text);

        assertEquals(owner1.getId(), result.getOwnerId());
        assertEquals(result.getBalance(), sum);
        assertEquals(result.getId(), accountDto1.getId());
    }


}