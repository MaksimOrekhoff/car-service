package ru.rikmasters.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query(value = "SELECT i FROM Owner AS i " +
            "WHERE :text <> '' " +
            "AND (upper(i.firstName) LIKE concat('%', upper(:text), '%') " +
            "OR upper(i.lastName) LIKE concat('%', upper(:text), '%'))")
    List<Owner> searchOwners(@Param("text") String text, MyPageRequest pageable);
}
