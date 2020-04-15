package ru.cbr.rrror.service.order.service;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.rrror.service.order.command.AddOrdinanceStatusHistory;
import ru.cbr.rrror.service.order.command.ConfirmFormeredOrdinance;
import ru.cbr.rrror.service.order.command.CreateConfirmedOrdinanceSpec;
import ru.cbr.rrror.service.order.command.ReturnConfirmedOrdinance;
import ru.cbr.rrror.service.order.entity.Ordinance;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class OrdinanceWriterService {

    @Autowired
    private AddOrdinanceStatusHistory addOrdinanceStatusHistory;

    @Autowired
    private ConfirmFormeredOrdinance confirmFormeredOrdinance;

    @Autowired
    private CreateConfirmedOrdinanceSpec createConfirmedOrdinanceSpec;

    @Autowired
    private ReturnConfirmedOrdinance returnConfirmedOrdinance;

    public void addOrdinanceStatusToHistory(Ordinance ordinance) {
        addOrdinanceStatusHistory.accept(ordinance);
    }

    public void confirmAndCreateOrdinanceSpec(Ordinance ordinance) {
        Objects.requireNonNull(ordinance);

        confirmFormeredOrdinance
                .andThen(createConfirmedOrdinanceSpec)
                .accept(ordinance);
    }

    public void returnConfirmedOrdinance(Pair<Ordinance, String> values) {
        Objects.requireNonNull(values);

        returnConfirmedOrdinance.accept(values);
    }

}
