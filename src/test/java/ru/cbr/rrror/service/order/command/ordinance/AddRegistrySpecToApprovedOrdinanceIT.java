package ru.cbr.rrror.service.order.command.ordinance;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.Registry;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
public class AddRegistrySpecToApprovedOrdinanceIT extends AbstractCommandTest {

    @Test
    public void addRegistryToApprovedOrdinanceWillSuccess() {
        Registry registry = createRegistry();
        Ordinance ordinance = formedOrdinanceSupplier.get();

        confirmFormeredOrdinance
                .andThen(approveConfirmedOrdinance)
                .accept(ordinance);

        assertNull(ordinance.getRegistrySpec());

        addRegistrySpecToApprovedOrdinance.accept(Pair.with(registry, ordinance));

        assertNotNull(ordinance.getRegistrySpec());
        assertTrue(ordinance.getRegistrySpec().getRegistryId().equals(registry.getId()));
        assertTrue(ordinance.getRegistrySpec().getRegistryNum().equals(registry.getRegistryNum()));
        assertNotNull(ordinance.getRegistrySpec().getName());

        log.debug(">>> " + ordinance.toString());
    }
}
