package ru.rikmasters.account;

public interface AccountService {
    AccountDto getAccount(Long ownerId, String text);

    void add(Long ownerId, Double sum, String text);

    void withdrawal(Long ownerId, Double sum, String text);

    AccountDto create(Long ownerId);

    void delete(Long accountId, Long ownerId);
}
