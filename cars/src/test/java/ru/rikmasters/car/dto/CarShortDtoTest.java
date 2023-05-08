package ru.rikmasters.car.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
@JsonTest
class CarShortDtoTest {
    @Autowired
    private JacksonTester<CarShortDto> jacksonTester;

    @Test
    void serialization() throws IOException {
        CarShortDto carShortDto = new CarShortDto(1L, "NewVin", "F123FF",
                "OpelConcern", "Opel", "2023-10-05", 1L);
        JsonContent<CarShortDto> result = jacksonTester.write(carShortDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.vin");
        assertThat(result).hasJsonPath("$.governmentNumber");
        assertThat(result).hasJsonPath("$.manufacturer");
        assertThat(result).hasJsonPath("$.brand");
        assertThat(result).hasJsonPath("$.yearOfIssue");
        assertThat(result).hasJsonPath("$.owner");

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(carShortDto.getId().intValue());
        assertThat(result).extractingJsonPathValue("$.vin").isEqualTo(carShortDto.getVin());
        assertThat(result).extractingJsonPathValue("$.governmentNumber").isEqualTo(carShortDto.getGovernmentNumber());
        assertThat(result).extractingJsonPathValue("$.manufacturer").isEqualTo(carShortDto.getManufacturer());
        assertThat(result).extractingJsonPathValue("$.brand").isEqualTo(carShortDto.getBrand());
        assertThat(result).extractingJsonPathValue("$.yearOfIssue").isEqualTo(carShortDto.getYearOfIssue());
        assertThat(result).extractingJsonPathValue("$.owner").isEqualTo(carShortDto.getOwner().intValue());
    }
}