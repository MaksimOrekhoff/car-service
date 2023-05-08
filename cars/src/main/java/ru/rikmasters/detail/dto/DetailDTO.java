package ru.rikmasters.detail.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DetailDTO {
    private Long id;
    @NotBlank(message = "Введите серийный номер.")
    private String serialNumber;
    @NotBlank(message = "Введите название детали.")
    private String denomination;
    @NotNull
    private Long car;

}
