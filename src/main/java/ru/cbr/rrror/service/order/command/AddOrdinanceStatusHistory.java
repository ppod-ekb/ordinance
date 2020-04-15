package ru.cbr.rrror.service.order.command;

import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatusHistory;

import java.util.function.Consumer;

@Component
public class AddOrdinanceStatusHistory implements Consumer<Ordinance> {

    @Override
    public void accept(Ordinance ordinance) {
        ordinance.getOrdinanceStatusHistories().add(new OrdinanceStatusHistory(ordinance));
    }
}
