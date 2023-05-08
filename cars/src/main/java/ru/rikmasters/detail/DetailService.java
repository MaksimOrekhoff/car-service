package ru.rikmasters.detail;

import ru.rikmasters.detail.dto.DetailDTO;

import java.util.List;

public interface DetailService {
    DetailDTO installation(DetailDTO detailDTO);

    void removeDetail(Long detailId);

    List<DetailDTO> getDetail(Long detailId);

    List<DetailDTO> getAllDetailsAndSortByDenomination(Integer from, Integer size);

    List<DetailDTO> searchDetails(String text, Integer from, Integer size);
}
