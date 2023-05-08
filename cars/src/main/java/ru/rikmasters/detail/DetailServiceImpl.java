package ru.rikmasters.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.rikmasters.car.Car;
import ru.rikmasters.car.CarRepository;
import ru.rikmasters.detail.dto.DetailDTO;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {
    private final DetailRepository detailRepository;
    private final DetailMapper detailMapper;
    private final CarRepository carRepository;

    @Override
    public DetailDTO installation(DetailDTO detailDTO) {
        Car car = carRepository.findById(detailDTO.getCar())
                .orElseThrow(() -> new NotFoundException("Такой автомобиль не существует."));
        if (!car.getDetails().isEmpty()) {
            List<Detail> details = car.getDetails()
                    .stream().filter(detail -> detail.getDenomination()
                            .equals(detailDTO.getDenomination())).toList();
            if (!details.isEmpty()) {
                car.getDetails().remove(details.get(0));
                detailRepository.delete(details.get(0));
            }
        }
        Detail detail = detailRepository.save(
                detailMapper.toDetail(List.of(detailDTO), car).get(0));
        log.debug("В автомобиль: {}, установлена деталь {}", car, detail);
        return detailMapper.toDetailDTO(List.of(detail)).get(0);
    }

    @Override
    public void removeDetail(Long detailId) {
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException("Такая деталь не существует."));
        detailRepository.deleteById(detailId);
        log.debug("Удалена деталь: {}", detail);
    }

    @Override
    public List<DetailDTO> getDetail(Long detailId) {
        Detail detail = detailRepository.findById(detailId)
                .orElseThrow(() -> new NotFoundException("Такая деталь не существует."));
        log.debug("Получена деталь: {}", detail);
        return detailMapper.toDetailDTO(List.of(detail));
    }

    @Override
    public List<DetailDTO> getAllDetailsAndSortByDenomination(Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("denomination"));
        Page<Detail> details = detailRepository.findAll(myPageRequest);
        log.debug("Получены все детали: {} и отсортированы по назначению.", details);
        return detailMapper.toDetailDTO(details.stream().toList());
    }

    @Override
    public List<DetailDTO> searchDetails(String text, Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("denomination"));
        List<Detail> details = detailRepository.searchOwners(text, myPageRequest);
        log.debug("Получены все детали: {} и отсортированы по назначению", details);
        return detailMapper.toDetailDTO(details.stream().toList());
    }

}
