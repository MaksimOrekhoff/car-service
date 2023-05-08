package ru.rikmasters.car.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarShortDto {
    private Long id;
    @NotBlank(message = "VIN не может быть пустым")
    private String vin;
    @NotBlank(message = "Гос. номер не может быть пустым")
    private String governmentNumber;
    private String manufacturer;
    private String brand;
    private String yearOfIssue;
    private Long owner;
}
