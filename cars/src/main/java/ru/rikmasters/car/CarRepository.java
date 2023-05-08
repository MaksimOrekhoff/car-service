package ru.rikmasters.car;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rikmasters.utils.MyPageRequest;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByYearOfIssueAfterAndYearOfIssueBefore(LocalDate start, LocalDate end, MyPageRequest myPageRequest);
}
