package ru.cbr.rrror.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.repository.OrdinanceStatusRepository;

@Component
public class DataLoader implements Runnable {

    @Autowired
    private OrdinanceStatusRepository repository;

    @Override
    public void run() {

    }
}
