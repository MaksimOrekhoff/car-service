package ru.rikmasters.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.rikmasters.account.dto.AccountDto;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
class AccountControllerTest {
    @MockBean
    AccountController accountController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    AccountDto accountDto;

    String path = "/accounts";

    @BeforeEach
    void startParam() {
        accountDto = new AccountDto(1L, 200D, 1L);
    }

    @Test
    void add() throws Exception {
        when(accountController.createAccount(accountDto.getOwnerId()))
                .thenReturn(accountDto);
        mockMvc.perform(post(path + "/1")
                        .content(mapper.writeValueAsString(accountDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(accountDto.getId()), Long.class))
                .andExpect(jsonPath("$.balance", is(accountDto.getBalance())))
                .andExpect(jsonPath("$.ownerId", is(accountDto.getOwnerId()), Long.class));

        verify(accountController, times(1))
                .createAccount(accountDto.getOwnerId());
    }

    @Test
    void getCar() throws Exception {
        when(accountController.getOwner(accountDto.getOwnerId(), "RED"))
                .thenReturn(accountDto);
        mockMvc.perform(get(path + "/" + 1)
                        .param("text", "RED")
                        .content(mapper.writeValueAsString(accountDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(accountDto.getId()), Long.class))
                .andExpect(jsonPath("$.balance", is(accountDto.getBalance())))
                .andExpect(jsonPath("$.ownerId", is(accountDto.getOwnerId()), Long.class));

        verify(accountController, times(1))
                .getOwner(accountDto.getOwnerId(), "RED");
    }
}