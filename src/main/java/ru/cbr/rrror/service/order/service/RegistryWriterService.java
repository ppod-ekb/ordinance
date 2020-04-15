package ru.cbr.rrror.service.order.service;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.rrror.service.order.command.AddRegistrySpecToApprovedOrdinance;
import ru.cbr.rrror.service.order.command.AddOrdinanceToRegistry;
import ru.cbr.rrror.service.order.command.CreateRegistry;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.Registry;

import javax.transaction.Transactional;

@Service
@Transactional
public class RegistryWriterService {

    @Autowired
    private CreateRegistry createRegistry;

    @Autowired
    private AddOrdinanceToRegistry addOrdinanceToRegistry;

    @Autowired
    private AddRegistrySpecToApprovedOrdinance addRegistrySpecToApprovedOrdinance;

   public Registry createRegistryAndAddOrdinances(Iterable<Ordinance> ordinances) {
       Registry registry = new Registry();
       createRegistry.accept(registry);

       ordinances.forEach(o -> addOrdinanceToRegistry
               .andThen(addRegistrySpecToApprovedOrdinance)
               .accept(Pair.with(registry,o)));

       return registry;
   }
}
