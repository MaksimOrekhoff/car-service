package ru.rikmasters.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
@JsonTest
class AccountDtoTest {
    @Autowired
    private JacksonTester<AccountDto> jacksonTester;

    @Test
    void serialization() throws IOException {
        AccountDto accountDto = new AccountDto(1L, 500D, 1L);
        JsonContent<AccountDto> result = jacksonTester.write(accountDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.balance");
        assertThat(result).hasJsonPath("$.ownerId");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(accountDto.getId().intValue());
        assertThat(result).extractingJsonPathValue("$.balance").isEqualTo(accountDto.getBalance());
        assertThat(result).extractingJsonPathValue("$.ownerId").isEqualTo( accountDto.getOwnerId().intValue());
    }
}