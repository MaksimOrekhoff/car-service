package ru.rikmasters.detail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class DetailRepositoryTest {
    @Autowired
    DetailRepository detailRepository;

    @Test
    void search() {
        Detail detail = new Detail(1L, "new", "kardan", null);

        detailRepository.save(detail);
        List<Detail> result = detailRepository.searchOwners("kar",
                new MyPageRequest(0, 10, Sort.unsorted()));

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(detail.getId(), result.get(0).getId());
        assertEquals(detail.getDenomination(), result.get(0).getDenomination());
        assertEquals(detail.getSerialNumber(), result.get(0).getSerialNumber());
    }
}