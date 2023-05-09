package ru.rikmasters.owner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping
    public OwnerCreateDTO createOwner(@Validated @RequestBody OwnerCreateDTO ownerCreateDTO) {
        log.info("Получен Post-запрос на добавление автовладельца {}", ownerCreateDTO);
        return ownerService.create(ownerCreateDTO);
    }

    @PostMapping("/{ownerId}/{carId}")
    public void setCarOwner(@PathVariable @NotBlank Long ownerId,
                            @PathVariable @NotBlank Long carId) {
        log.info("Получен Post-запрос на владение водителем с id: {} автомобилем с id: {}", ownerId, carId);
        ownerService.setOwner(ownerId, carId);
    }

    @DeleteMapping("/deleteCar/{ownerId}")
    public void deleteOwnerCar(@PathVariable Long ownerId) {
        log.info("Получен Delete-запрос на отчуждение автомобиля от автовладельца c id: {}", ownerId);
        ownerService.deleteAuto(ownerId);
    }

    @GetMapping("/{ownerId}")
    public OwnerDTO getOwner(@PathVariable @NotBlank Long ownerId) {
        log.info("Получен Get-запрос на получение автовладельца с id: {}.", ownerId);
        return ownerService.getOwner(ownerId);
    }

    @DeleteMapping("/{ownerId}")
    public void deleteOwner(@PathVariable @NotBlank Long ownerId) {
        log.info("Получен Delete-запрос на удаление автовладельца c id: {}", ownerId);
        ownerService.removeOwner(ownerId);
    }

    @PatchMapping("/{ownerId}")
    public OwnerDTO patchOwner(@Validated @RequestBody OwnerDTO ownerDTO,
                               @PathVariable @NotBlank Long ownerId) {
        log.info("Получен Patch-запрос на изменение автовладельца {} c id {}:", ownerDTO, ownerId);
        return ownerService.patchOwner(ownerDTO, ownerId);
    }

    @PutMapping ("/changeOwner/{ownerId}/{carId}")
    public void changeOwnerCar(@PathVariable @NotBlank Long ownerId,
                               @PathVariable @NotBlank Long carId) {
        log.info("Получен Patch-запрос на смену владельцем {} автомобиля c id {}:", ownerId, carId);
        ownerService.setNewOwner(ownerId, carId);
    }

    @GetMapping()
    public List<OwnerDTO> allOwnersAndSortByPassport(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                     @PositiveOrZero @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен Get-запрос на получение всех автовладельцей с сортировкой по паспорту и пагинацией");
        return ownerService.getAllOwnersAndSort(from, size);
    }

    @GetMapping("/search")
    public List<OwnerDTO> searchOwnersByParam(@RequestParam final String text,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @PositiveOrZero @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен Get-запрос на получение всех автовладельцем содержащих в личных данных {}", text);
        return ownerService.searchOwners(text, from, size);
    }

}
