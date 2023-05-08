package ru.rikmasters.detail.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class DetailDTOTest {
    @Autowired
    private JacksonTester<DetailDTO> jacksonTester;

    @Test
    void serialization() throws IOException {
        DetailDTO detailDTO = new DetailDTO(1L, "EJR(F#FH", "Kardan", 1L);
        JsonContent<DetailDTO> result = jacksonTester.write(detailDTO);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.serialNumber");
        assertThat(result).hasJsonPath("$.denomination");
        assertThat(result).hasJsonPath("$.car");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathValue("$.serialNumber").isEqualTo(detailDTO.getSerialNumber());
        assertThat(result).extractingJsonPathValue("$.denomination").isEqualTo(detailDTO.getDenomination());
        assertThat(result).extractingJsonPathValue("$.car").isEqualTo(1);
    }
}