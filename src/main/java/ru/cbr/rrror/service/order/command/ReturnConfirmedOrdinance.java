package ru.cbr.rrror.service.order.command;

import org.javatuples.Pair;
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
public class ReturnConfirmedOrdinance implements Consumer<Pair<Ordinance, String>> {

    @Autowired
    private OrdinanceStatusRepository statusRepository;

    @Override
    public void accept(Pair<Ordinance, String> values) {
        Objects.requireNonNull(values, "values can't be null");

        Ordinance ordinance = values.getValue0();
        String reason  = values.getValue1();

        Assert.notNull(ordinance, ()->"Ordinance can't be null");
        Assert.hasText(reason, ()->"can't return Ordinance without reason message");

        Assert.state(new OrdinanceStatusCheck(OrdinanceStatus.Code.CONFIRMED).check(ordinance), ()->"can't return non confirmed Ordinance");

        OrdinanceStatus ordinanceStatus = statusRepository.findOneByCode(OrdinanceStatus.Code.RETURNED)
                                                            .orElseThrow(OrdinanceStatusNotPresentException::new);

        ordinance.setOrdinanceStatus(ordinanceStatus);
        ordinance.setReason(reason);
    }
}
