package ru.cbr.rrror.service.order.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;

@Repository
public interface OrdinanceRepository extends PagingAndSortingRepository<Ordinance, Long> {

    @Query("select o from Ordinance o where o.ordinanceStatus.code = ?1")
    Iterable<Ordinance> findAllByOrdinanceStatus(OrdinanceStatus.Code code);
}
