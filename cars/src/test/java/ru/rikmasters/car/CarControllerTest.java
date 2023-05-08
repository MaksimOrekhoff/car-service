package ru.rikmasters.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.rikmasters.car.dto.CarCreateDTO;
import ru.rikmasters.car.dto.CarDTO;
import ru.rikmasters.car.dto.CarShortDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc
class CarControllerTest {
    @MockBean
    CarsService carsService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    CarCreateDTO carCreateDTO;
    CarDTO carDTO;
    CarShortDto carShortDto;
    String path = "/cars";

    @BeforeEach
    void startParam() {
        carCreateDTO = new CarCreateDTO(1L, "vin", "number", "opel",
                "brand", LocalDate.of(2020, 10, 15), new ArrayList<>());
        carDTO = new CarDTO(2L, "vin2", "number", "opel",
                "brand", "2023-05-08", new ArrayList<>(), 2L);
        carShortDto = new CarShortDto(3L, "vin3", "number", "opel",
                "brand", "2025-05-08", 3L);
    }

    @Test
    void add() throws Exception {
        when(carsService.create(carCreateDTO))
                .thenReturn(carCreateDTO);
        mockMvc.perform(post(path)
                        .content(mapper.writeValueAsString(carCreateDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carCreateDTO.getId()), Long.class))
                .andExpect(jsonPath("$.vin", is(carCreateDTO.getVin())))
                .andExpect(jsonPath("$.governmentNumber", is(carCreateDTO.getGovernmentNumber())))
                .andExpect(jsonPath("$.manufacturer", is(carCreateDTO.getManufacturer())))
                .andExpect(jsonPath("$.brand", is(carCreateDTO.getBrand())));

        verify(carsService, times(1))
                .create(carCreateDTO);
    }

    @Test
    void getCar() throws Exception {
        when(carsService.getCar(carDTO.getId()))
                .thenReturn(carDTO);
        mockMvc.perform(get(path + "/" + 2)
                        .content(mapper.writeValueAsString(carDTO))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carDTO.getId()), Long.class))
                .andExpect(jsonPath("$.vin", is(carDTO.getVin())))
                .andExpect(jsonPath("$.governmentNumber", is(carDTO.getGovernmentNumber())))
                .andExpect(jsonPath("$.manufacturer", is(carDTO.getManufacturer())))
                .andExpect(jsonPath("$.brand", is(carDTO.getBrand())));

        verify(carsService, times(1))
                .getCar(carDTO.getId());
    }

    @Test
    void getAllCars() throws Exception {
        when(carsService.getAllCars(0, 10))
                .thenReturn(Collections.singletonList(carShortDto));

        mockMvc.perform(get(path)
                        .param("from", "0")
                        .param("size", "10")
                        .content(mapper.writeValueAsString(carShortDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(carShortDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].vin", is(carShortDto.getVin())))
                .andExpect(jsonPath("$[0].governmentNumber", is(carShortDto.getGovernmentNumber())))
                .andExpect(jsonPath("$[0].manufacturer", is(carShortDto.getManufacturer())))
                .andExpect(jsonPath("$[0].brand", is(carShortDto.getBrand())));

        verify(carsService, times(1))
                .getAllCars(0, 10);
    }
}