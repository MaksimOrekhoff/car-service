package ru.rikmasters.detail;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.rikmasters.detail.dto.DetailDTO;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/details")
public class DetailController {
    private final DetailService detailService;

    @PutMapping
    public DetailDTO installationDetail(@Validated @RequestBody DetailDTO detailDTO) {
        log.info("Получен Post-запрос на добавление детали {}", detailDTO);
        return detailService.installation(detailDTO);
    }

    @DeleteMapping("/{detailId}")
    public void deleteDetail(@PathVariable @NotBlank Long detailId) {
        log.info("Получен Delete-запрос на удаление детали c id: {}", detailId);
        detailService.removeDetail(detailId);
    }

    @GetMapping("/{detailId}")
    public List<DetailDTO> getDetail(@PathVariable @NotBlank Long detailId) {
        log.info("Получен Get-запрос на получение детали с id: {}.", detailId);
        return detailService.getDetail(detailId);
    }

    @GetMapping()
    public List<DetailDTO> allDetails(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен Get-запрос на получение всех деталей с пагинацией.");
        return detailService.getAllDetailsAndSortByDenomination(from, size);
    }

    @GetMapping("/search")
    public List<DetailDTO> searchDetailsByParam(@RequestParam final String text,
                                                @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получен Get-запрос на получение всех деталей содержащих в названии {}", text);
        return detailService.searchDetails(text, from, size);
    }
}
