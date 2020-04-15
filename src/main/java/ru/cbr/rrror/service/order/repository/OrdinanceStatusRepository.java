package ru.cbr.rrror.service.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;

import java.util.Optional;

@Repository
public interface OrdinanceStatusRepository extends JpaRepository<OrdinanceStatus, Integer> {

    Optional<OrdinanceStatus> findOneByCode(OrdinanceStatus.Code code);
}
