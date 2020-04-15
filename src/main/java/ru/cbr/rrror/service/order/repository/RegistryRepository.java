package ru.cbr.rrror.service.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cbr.rrror.service.order.entity.Registry;

@Repository
public interface RegistryRepository extends CrudRepository<Registry, Long> {

}
