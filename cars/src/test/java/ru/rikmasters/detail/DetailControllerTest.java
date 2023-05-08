package ru.rikmasters.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.rikmasters.detail.dto.DetailDTO;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DetailController.class)
@AutoConfigureMockMvc
class DetailControllerTest {
    @MockBean
    DetailService detailService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    DetailDTO detailDTO;
    String path = "/details";
    @BeforeEach
    void startParam() {
        detailDTO = new DetailDTO(1L, "EJR(F#FH", "Kardan", 1L);
    }

    @Test
    void add() throws Exception {
        when(detailService.installation(detailDTO))
                .thenReturn(detailDTO);
        mockMvc.perform(put(path)
                        .content(mapper.writeValueAsString(detailDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(detailDTO.getId()), Long.class))
                .andExpect(jsonPath("$.serialNumber", is(detailDTO.getSerialNumber())))
                .andExpect(jsonPath("$.denomination", is(detailDTO.getDenomination())))
                .andExpect(jsonPath("$.car", is(detailDTO.getCar()), Long.class));

        verify(detailService, times(1))
                .installation(detailDTO);
    }

    @Test
    void getAllDetails() throws Exception {
        when(detailService.getAllDetailsAndSortByDenomination(0, 10))
                .thenReturn(Collections.singletonList(detailDTO));

        mockMvc.perform(get(path)
                        .param("from", "0")
                        .param("size", "10")
                        .content(mapper.writeValueAsString(detailDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(detailDTO.getId()), Long.class))
                .andExpect(jsonPath("$[0].serialNumber", is(detailDTO.getSerialNumber())))
                .andExpect(jsonPath("$[0].denomination", is(detailDTO.getDenomination())))
                .andExpect(jsonPath("$[0].car", is(detailDTO.getCar()), Long.class));

        verify(detailService, times(1))
                .getAllDetailsAndSortByDenomination(0, 10);
    }
}