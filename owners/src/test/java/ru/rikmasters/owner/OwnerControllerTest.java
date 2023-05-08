package ru.rikmasters.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@AutoConfigureMockMvc
class OwnerControllerTest {
    @MockBean
    OwnerController ownerController;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    OwnerCreateDTO ownerCreateDTO;
    OwnerDTO ownerDTO;
    String path = "/owners";

    @BeforeEach
    void startParam() {
        ownerCreateDTO = new OwnerCreateDTO(1L, "f", "m",
                "l", "a", "B", "2", 5);
        ownerDTO = new OwnerDTO(2L, "f", "m",
                "l", "a", "B", "2", 5, 2L);
    }

    @Test
    void add() throws Exception {
        when(ownerController.createOwner(ownerCreateDTO))
                .thenReturn(ownerCreateDTO);
        mockMvc.perform(post(path)
                        .content(mapper.writeValueAsString(ownerCreateDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ownerCreateDTO.getId()), Long.class))
                .andExpect(jsonPath("$.firstName", is(ownerCreateDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(ownerCreateDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(ownerCreateDTO.getLastName())))
                .andExpect(jsonPath("$.passport", is(ownerCreateDTO.getPassport())))
                .andExpect(jsonPath("$.driversLicense", is(ownerCreateDTO.getDriversLicense())))
                .andExpect(jsonPath("$.experience", is(ownerCreateDTO.getExperience())));

        verify(ownerController, times(1))
                .createOwner(ownerCreateDTO);
    }

    @Test
    void getAllOwners() throws Exception {
        when(ownerController.allOwnersAndSortByPassport(0, 10))
                .thenReturn(Collections.singletonList(ownerDTO));

        mockMvc.perform(get(path)
                        .param("from", "0")
                        .param("size", "10")
                        .content(mapper.writeValueAsString(ownerDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(ownerDTO.getId()), Long.class))
                .andExpect(jsonPath("$[0].firstName", is(ownerDTO.getFirstName())))
                .andExpect(jsonPath("$[0].middleName", is(ownerDTO.getMiddleName())))
                .andExpect(jsonPath("$[0].lastName", is(ownerDTO.getLastName())))
                .andExpect(jsonPath("$[0].passport", is(ownerDTO.getPassport())))
                .andExpect(jsonPath("$[0].driversLicense", is(ownerDTO.getDriversLicense())))
                .andExpect(jsonPath("$[0].experience", is(ownerDTO.getExperience())))
                .andExpect(jsonPath("$[0].car", is(ownerDTO.getCar()), Long.class));

        verify(ownerController, times(1))
                .allOwnersAndSortByPassport(0, 10);
    }

}