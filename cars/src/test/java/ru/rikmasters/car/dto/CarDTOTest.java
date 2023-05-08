package ru.rikmasters.car.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
@JsonTest
class CarDTOTest {
    @Autowired
    private JacksonTester<CarDTO> jacksonTester;

    @Test
    void serialization() throws IOException {
        CarDTO carDTO = new CarDTO(1L, "NewVin", "F123FF",
                "OpelConcern", "Opel", "2023-10-05", new ArrayList<>(), 1L);
        JsonContent<CarDTO> result = jacksonTester.write(carDTO);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.vin");
        assertThat(result).hasJsonPath("$.governmentNumber");
        assertThat(result).hasJsonPath("$.manufacturer");
        assertThat(result).hasJsonPath("$.brand");
        assertThat(result).hasJsonPath("$.yearOfIssue");
        assertThat(result).hasJsonPath("$.owner");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(carDTO.getId().intValue());
        assertThat(result).extractingJsonPathValue("$.vin").isEqualTo(carDTO.getVin());
        assertThat(result).extractingJsonPathValue("$.governmentNumber").isEqualTo(carDTO.getGovernmentNumber());
        assertThat(result).extractingJsonPathValue("$.manufacturer").isEqualTo(carDTO.getManufacturer());
        assertThat(result).extractingJsonPathValue("$.brand").isEqualTo(carDTO.getBrand());
        assertThat(result).extractingJsonPathValue("$.yearOfIssue").isEqualTo(carDTO.getYearOfIssue());
        assertThat(result).extractingJsonPathValue("$.owner").isEqualTo(carDTO.getId().intValue());
    }
}