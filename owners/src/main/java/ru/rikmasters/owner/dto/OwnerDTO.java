package ru.rikmasters.owner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rikmasters.utils.DriversLicense;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OwnerDTO {
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;
    @NotBlank(message = "Введите отчество")
    private String middleName;
    @NotBlank(message = "Введите фамилию")
    private String lastName;
    @NotBlank(message = "Укажите паспотрные данные")
    private String passport;
    @NotBlank(message = "Не указана категория прав")
    private String driversLicense;
    @NotBlank(message = "Укажите дату рождения")
    private String dateOfBirth;
    @NotNull(message = "Введите водительский стаж")
    private Integer experience;
    private Long car;

}
