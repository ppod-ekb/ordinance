package ru.cbr.rrror.service.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.exception.OrdinanceNotFoundException;
import ru.cbr.rrror.service.order.repository.OrdinanceRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class OrdinanceReaderService {

    @Autowired
    private OrdinanceRepository repository;

    public Ordinance byId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrdinanceNotFoundException(id));
    }
}
