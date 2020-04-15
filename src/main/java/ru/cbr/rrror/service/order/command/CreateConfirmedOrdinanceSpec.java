package ru.cbr.rrror.service.order.command;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.util.OrdinanceStatusCheck;

import java.time.LocalDate;
import java.util.function.Consumer;

@Component
public class CreateConfirmedOrdinanceSpec implements Consumer<Ordinance> {

    @Override
    public void accept(Ordinance ordinance) {
        Assert.state(new OrdinanceStatusCheck(OrdinanceStatus.Code.CONFIRMED).check(ordinance),
                "can't create spec for non confirmed Ordinance ");
        ordinance.setOrderSpec(
                new Ordinance.OrderSpec(
                        ordinance.getId().toString(),
                        LocalDate.now()));
    }
}
