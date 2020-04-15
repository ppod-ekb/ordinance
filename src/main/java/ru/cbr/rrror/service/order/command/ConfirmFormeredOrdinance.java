package ru.cbr.rrror.service.order.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.exception.OrdinanceStatusNotPresentException;
import ru.cbr.rrror.service.order.repository.OrdinanceStatusRepository;
import ru.cbr.rrror.service.order.util.OrdinanceStatusCheck;

import java.util.Objects;
import java.util.function.Consumer;

@Component
public class ConfirmFormeredOrdinance implements Consumer<Ordinance> {

    @Autowired
    private OrdinanceStatusRepository statusRepository;

    @Override
    public void accept(Ordinance ordinance) {
        Objects.requireNonNull(ordinance, "Ordinance can't be null");

        Assert.state(new OrdinanceStatusCheck(
                OrdinanceStatus.Code.FORMED,
                OrdinanceStatus.Code.RETURNED).check(ordinance), "can't confirm non formered or returned Ordinance");

        OrdinanceStatus status = statusRepository
                .findOneByCode(OrdinanceStatus.Code.CONFIRMED)
                .orElseThrow(OrdinanceStatusNotPresentException::new);

        ordinance.setOrdinanceStatus(status);
    }
}
