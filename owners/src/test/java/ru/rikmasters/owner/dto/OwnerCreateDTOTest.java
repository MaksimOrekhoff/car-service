package ru.rikmasters.owner.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
@JsonTest
class OwnerCreateDTOTest {
    @Autowired
    private JacksonTester<OwnerCreateDTO> jacksonTester;

    @Test
    void serialization() throws IOException {
        OwnerCreateDTO ownerCreateDTO = new OwnerCreateDTO(1L, "NewVin", "F123FF",
                "OpelConcern", "Opel", "B", "2023-10-05", 1);
        JsonContent<OwnerCreateDTO> result = jacksonTester.write(ownerCreateDTO);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.firstName");
        assertThat(result).hasJsonPath("$.middleName");
        assertThat(result).hasJsonPath("$.lastName");
        assertThat(result).hasJsonPath("$.passport");
        assertThat(result).hasJsonPath("$.driversLicense");
        assertThat(result).hasJsonPath("$.dateOfBirth");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(ownerCreateDTO.getId().intValue());
        assertThat(result).extractingJsonPathValue("$.firstName").isEqualTo(ownerCreateDTO.getFirstName());
        assertThat(result).extractingJsonPathValue("$.middleName").isEqualTo(ownerCreateDTO.getMiddleName());
        assertThat(result).extractingJsonPathValue("$.lastName").isEqualTo(ownerCreateDTO.getLastName());
        assertThat(result).extractingJsonPathValue("$.passport").isEqualTo(ownerCreateDTO.getPassport());
        assertThat(result).extractingJsonPathValue("$.driversLicense").isEqualTo(ownerCreateDTO.getDriversLicense());
        assertThat(result).extractingJsonPathValue("$.dateOfBirth").isEqualTo(ownerCreateDTO.getDateOfBirth());
    }
}