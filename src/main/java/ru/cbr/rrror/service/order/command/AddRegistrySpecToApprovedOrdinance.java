package ru.cbr.rrror.service.order.command;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.Registry;

import java.util.function.Consumer;

@Component
public class AddRegistrySpecToApprovedOrdinance implements Consumer<Pair<Registry, Ordinance>> {

    @Override
    public void accept(Pair<Registry, Ordinance> objects) {
        Registry registry = objects.getValue0();
        Ordinance ordinance = objects.getValue1();

        ordinance.setRegistrySpec(new Ordinance.RegistrySpec(registry));
    }
}
