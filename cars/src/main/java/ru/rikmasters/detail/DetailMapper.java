package ru.rikmasters.detail;

import org.springframework.stereotype.Component;
import ru.rikmasters.car.Car;
import ru.rikmasters.detail.dto.DetailDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class DetailMapper {
    public List<Detail> toDetail(List<DetailDTO> detailDTO, Car car) {
        List<Detail> details = new ArrayList<>();
        for (DetailDTO d : detailDTO) {
            details.add(Detail.builder()
                    .denomination(d.getDenomination())
                    .id(d.getId())
                    .serialNumber(d.getSerialNumber())
                    .car(car)
                    .build());
        }
        return details;
    }

    public List<DetailDTO> toDetailDTO(List<Detail> detail) {
        List<DetailDTO> details = new ArrayList<>();
        for (Detail d : detail) {
            details.add(DetailDTO.builder()
                    .id(d.getId())
                    .denomination(d.getDenomination())
                    .serialNumber(d.getSerialNumber())
                    .car(d.getCar().getId())
                    .build());
        }
        return details;
    }

    public Detail toPatchDetailDTO(DetailDTO detailDTO, Detail detail) {
        detail.setDenomination(detailDTO.getDenomination() != null ?
                detailDTO.getDenomination() : detail.getDenomination() != null ?
                detail.getDenomination() : null);
        detail.setSerialNumber(detailDTO.getSerialNumber() != null ?
                detailDTO.getSerialNumber() : detail.getSerialNumber() != null ?
                detail.getSerialNumber() : null);
        return detail;
    }
}
