package ru.cbr.rrror.service.order.command;

import org.springframework.beans.factory.annotation.Autowired;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.entity.Registry;
import ru.cbr.rrror.service.order.repository.OrdinanceRepository;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCommandTest {
    private static final Long ORDINANCE_ID = 1L;

    @Autowired
    protected OrdinanceRepository repository;

    @Autowired
    protected ConfirmFormeredOrdinance confirmFormeredOrdinance;

    @Autowired
    protected ReturnConfirmedOrdinance returnConfirmedOrdinance;

    @Autowired
    protected ApproveConfirmedOrdinance approveConfirmedOrdinance;

    @Autowired
    protected CreateConfirmedOrdinanceSpec createConfirmedOrdinanceSpec;

    @Autowired
    protected CreateRegistry createRegistry;

    @Autowired
    protected AddOrdinanceToRegistry addOrdinanceToRegistry;

    @Autowired
    protected AddRegistrySpecToApprovedOrdinance addRegistrySpecToApprovedOrdinance;

    @Autowired
    protected AddOrdinanceStatusHistory addOrdinanceStatusHistory;


    protected Registry createRegistry() {
        Registry registry = new Registry();
        createRegistry.accept(registry);
        return registry;
    }

    protected Supplier<Ordinance> formedOrdinanceSupplier = () -> {
        Optional<Ordinance> ordinanceById = repository.findById(ORDINANCE_ID);
        assertTrue(ordinanceById.isPresent(), "can't get ordinance by id: " + ORDINANCE_ID);
        Ordinance ordinance = ordinanceById.get();
        assertTrue(ordinance.getOrdinanceStatus().getCode().equals(OrdinanceStatus.Code.FORMED));

        return ordinance;
    };


}
