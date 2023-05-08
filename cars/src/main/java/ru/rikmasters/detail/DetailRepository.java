package ru.rikmasters.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    @Query(value = "SELECT i FROM Detail AS i " +
            "WHERE :text <> '' " +
            "AND (upper(i.denomination) LIKE concat('%', upper(:text), '%'))")
    List<Detail> searchOwners(@Param("text") String text, MyPageRequest pageable);
}
