package ru.rikmasters.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{ownerId}")
    public AccountDto getOwner(@PathVariable @NotBlank Long ownerId,
                               @RequestParam final String text) {
        log.info("Получен Get-запрос на получение состояния счета автовладельца с id: {}. в {} долларах", ownerId, text);
        return accountService.getAccount(ownerId, text);
    }

    @PostMapping("/{ownerId}")
    public AccountDto createAccount(@PathVariable @NotBlank Long ownerId) {
        log.info("Получен Post-запрос на открытие счета автовладельца {}", ownerId);
        return accountService.create(ownerId);
    }

    @PatchMapping("replenishmentAccount/{ownerId}")
    public void addAccount(@PathVariable @NotBlank Long ownerId,
                           @RequestParam final String text,
                           @RequestParam final Double sum) {
        log.info("Получен Patch-запрос на изменение счета автовладельца с id: {}. в {} долларах", ownerId, text);
        accountService.add(ownerId, sum, text);
    }

    @PatchMapping("withdrawalFacilities/{ownerId}")
    public void withdrawal(@PathVariable @NotBlank Long ownerId,
                           @RequestParam final String text,
                           @RequestParam final Double sum) {
        log.info("Получен Patch-запрос на изменение счета автовладельца с id: {}. в {} долларах", ownerId, text);
        accountService.withdrawal(ownerId, sum, text);
    }

    @DeleteMapping("/{accountId}/{ownerId}")
    public void deleteAccount(@PathVariable Long ownerId,
                              @PathVariable Long accountId) {
        log.info("Получен Delete-запрос на удаление счета {} автовладельца c id: {}", accountId, ownerId);
        accountService.delete(accountId, ownerId);
    }

}
