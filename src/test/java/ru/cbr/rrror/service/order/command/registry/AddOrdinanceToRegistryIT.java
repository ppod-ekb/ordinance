package ru.cbr.rrror.service.order.command.registry;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.Registry;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@Transactional
public class AddOrdinanceToRegistryIT extends AbstractCommandTest {

    @Test
    public void addApprovedOrdinanceToRegistryWillSuccess() {
        Registry registry = createRegistry();
        Ordinance ordinance = formedOrdinanceSupplier.get();

        confirmFormeredOrdinance
                .andThen(approveConfirmedOrdinance)
                .accept(ordinance);

        assertTrue(registry.getEntries().isEmpty());

        addOrdinanceToRegistry
                .accept(Pair.with(registry,ordinance));

        assertFalse(registry.getEntries().isEmpty());

        log.debug(">>> " + ordinance.toString());
        log.debug(">>> " + registry.toString());
    }

}
