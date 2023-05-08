package ru.rikmasters.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.account.dto.AccountDto;
import ru.rikmasters.exception.BadRequest;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.utils.Currency;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountDto getAccount(Long ownerId, String text) {
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой счет не существует."));
        log.debug("Получен счет {} автовладелеца {} ", account, ownerId);
        return getAccountDto(text, account);
    }

    @Override
    public void add(Long ownerId, Double sum, String text) {
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой счет не существует."));
        addAccount(account, sum, text);
        log.debug("Пополнение счета {} автовладелеца {} на {} {} долларов", account, ownerId, sum, text);
    }

    @Override
    public void withdrawal(Long ownerId, Double sum, String text) {
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой счет не существует."));
        withdrawalSum(account, sum, text);
        log.debug("Списание со счета {} автовладелеца {} на сумму {} {} долларов", account, ownerId, sum, text);

    }

    @Override
    public AccountDto create(Long ownerId) {
        Account account = accountRepository.save(
                new Account(null, 0D, 0D, 0D, ownerId));
        log.debug("Открыт счет {} автовладелеца {} ", account, ownerId);
        return new AccountDto(account.getId(), null, ownerId);
    }

    @Override
    public void delete(Long accountId, Long ownerId) {
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой счет не существует."));
        if (!account.getOwnerId().equals(ownerId)) {
            throw new BadRequest("Счет принадлежит другому владельцу.");
        }
        accountRepository.delete(account);
        log.debug("Удален счет {} автовладелеца {} ", account, ownerId);

    }

    private void addAccount(Account account, Double sum, String text) {
        Currency currency = Currency.valueOf(text);
        switch (currency) {
            case GREEN -> addGreen(account, sum);
            case RED -> addRed(account, sum);
            case BLUE -> addBlue(account, sum);
            default -> throw new BadRequest("Неверный парамерт запроса");
        }

    }

    private void withdrawalSum(Account account, Double sum, String text) {
        Currency currency = Currency.valueOf(text);
        switch (currency) {
            case GREEN -> withdrawalGreen(account, sum);
            case RED -> withdrawalRed(account, sum);
            case BLUE -> withdrawalBlue(account, sum);
            default -> throw new BadRequest("Неверный парамерт запроса");
        }

    }

    private void withdrawalBlue(Account account, Double sum) {
        if (account.getBlueBalance() - sum < 0) {
            throw new BadRequest("Недостаточно средств на счете");
        }
        account.setGreenBalance(account.getGreenBalance() - sum / 0.6);
        account.setRedBalance(account.getRedBalance() - sum / 0.6 / 2.5);
        account.setBlueBalance(account.getBlueBalance() - sum);
        accountRepository.save(account);
    }

    private void withdrawalRed(Account account, Double sum) {
        if (account.getRedBalance() - sum < 0) {
            throw new BadRequest("Недостаточно средств на счете");
        }
        account.setGreenBalance(account.getGreenBalance() - sum / 2.5);
        account.setRedBalance(account.getRedBalance() - sum);
        account.setBlueBalance(account.getBlueBalance() - sum / 2.5 * 0.6);
        accountRepository.save(account);
    }

    private void withdrawalGreen(Account account, Double sum) {
        if (account.getGreenBalance() - sum < 0) {
            throw new BadRequest("Недостаточно средств на счете");
        }
        account.setGreenBalance(account.getGreenBalance() - sum);
        account.setRedBalance(account.getRedBalance() - sum / 2.5);
        account.setBlueBalance(account.getBlueBalance() - sum * 0.6);
        accountRepository.save(account);
    }

    private void addBlue(Account account, Double sum) {
        account.setGreenBalance(account.getGreenBalance() + sum / 0.6);
        account.setRedBalance(account.getRedBalance() + sum / 0.6 / 2.5);
        account.setBlueBalance(account.getBlueBalance() + sum);
        accountRepository.save(account);
    }

    private void addRed(Account account, Double sum) {
        account.setGreenBalance(account.getGreenBalance() + sum / 2.5);
        account.setRedBalance(account.getRedBalance() + sum);
        account.setBlueBalance(account.getBlueBalance() + (sum / 2.5 * 0.6));
        accountRepository.save(account);
    }

    private void addGreen(Account account, Double sum) {
        account.setGreenBalance(account.getGreenBalance() + sum);
        account.setRedBalance(account.getRedBalance() + sum / 2.5);
        account.setBlueBalance(account.getBlueBalance() + sum * 0.6);
        accountRepository.save(account);
    }

    private AccountDto getAccountDto(String text, Account account) {
        AccountDto accountDto = new AccountDto();
        Currency currency = Currency.valueOf(text);
        switch (currency) {
            case GREEN -> accountDto.setBalance(account.getGreenBalance());
            case RED -> accountDto.setBalance(account.getRedBalance());
            case BLUE -> accountDto.setBalance(account.getBlueBalance());
        }
        accountDto.setId(account.getId());
        accountDto.setOwnerId(account.getOwnerId());
        return accountDto;
    }
}




