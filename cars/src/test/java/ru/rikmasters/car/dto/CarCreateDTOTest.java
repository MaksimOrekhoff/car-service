package ru.rikmasters.car.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CarCreateDTOTest {
    @Autowired
    private JacksonTester<CarCreateDTO> jacksonTester;

    @Test
    void serialization() throws IOException {
        CarCreateDTO carCreateDTO = new CarCreateDTO(1L, "NewVin", "F123FF",
                "OpelConcern", "Opel", LocalDate.now().plusDays(1), new ArrayList<>());
        JsonContent<CarCreateDTO> result = jacksonTester.write(carCreateDTO);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.vin");
        assertThat(result).hasJsonPath("$.governmentNumber");
        assertThat(result).hasJsonPath("$.manufacturer");
        assertThat(result).hasJsonPath("$.brand");
        assertThat(result).hasJsonPath("$.yearOfIssue");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.vin").isEqualTo(carCreateDTO.getVin());
        assertThat(result).extractingJsonPathValue("$.governmentNumber").isEqualTo(carCreateDTO.getGovernmentNumber());
        assertThat(result).extractingJsonPathValue("$.manufacturer").isEqualTo(carCreateDTO.getManufacturer());
        assertThat(result).extractingJsonPathValue("$.brand").isEqualTo(carCreateDTO.getBrand());
        assertThat(result).extractingJsonPathValue("$.yearOfIssue").isEqualTo(carCreateDTO.getYearOfIssue().toString());
    }
}