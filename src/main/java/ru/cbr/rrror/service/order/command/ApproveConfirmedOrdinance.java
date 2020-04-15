package ru.cbr.rrror.service.order.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.repository.OrdinanceStatusRepository;
import ru.cbr.rrror.service.order.util.OrdinanceStatusCheck;

import java.util.function.Consumer;

@Component
public class ApproveConfirmedOrdinance implements Consumer<Ordinance> {

    @Autowired
    private OrdinanceStatusRepository statusRepository;

    @Override
    public void accept(Ordinance ordinance) {
        Assert.state(new OrdinanceStatusCheck(OrdinanceStatus.Code.CONFIRMED)
                .check(ordinance), "can't approve non confirmed Ordinance");

        statusRepository
                .findOneByCode(OrdinanceStatus.Code.APPROVED)
                .ifPresent(ordinance::setOrdinanceStatus);
    }
}
