package ru.cbr.rrror.service.order.command.ordinance;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@Transactional
public class AddOrdinanceStatusHistoryIT extends AbstractCommandTest {

    @Test
    public void addOrdinanceStatusHistoryWillSuccess() {
        Ordinance ordinance = formedOrdinanceSupplier.get();
        confirmFormeredOrdinance
                .andThen(addOrdinanceStatusHistory);

        returnConfirmedOrdinance.accept(Pair.with(ordinance, "test"));
        addOrdinanceStatusHistory.accept(ordinance);

        repository.save(ordinance);

        log.debug(">>> " + ordinance.toString());
    }
}
