package ru.cbr.rrror.service.order.command;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.entity.Registry;
import ru.cbr.rrror.service.order.entity.RegistryEntry;
import ru.cbr.rrror.service.order.util.OrdinanceStatusCheck;

import java.util.function.Consumer;

@Component
public class AddOrdinanceToRegistry implements Consumer<Pair<Registry, Ordinance>> {

    @Override
    public void accept(Pair<Registry, Ordinance> pair) {
        Registry registry = pair.getValue0();
        Ordinance ordinance = pair.getValue1();

        Assert.state(new OrdinanceStatusCheck(OrdinanceStatus.Code.APPROVED).check(ordinance),
                "can't add non approved Ordinance to registry");

        registry.addEntry(new RegistryEntry(ordinance));
    }
}
