package ru.rikmasters.car.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rikmasters.detail.dto.DetailDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarDTO {
    private Long id;
    @NotBlank(message = "VIN не может быть пустым")
    private String vin;
    @NotBlank(message = "Гос. номер не может быть пустым")
    private String governmentNumber;
    private String manufacturer;
    private String brand;
    private String yearOfIssue;
    private List<DetailDTO> details = new ArrayList<>();
    private Long owner;
}
