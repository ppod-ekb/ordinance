package ru.cbr.rrror.service.order.command.ordinance;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;

import javax.transaction.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class ReturnConfirmedOrdinanceIT extends AbstractCommandTest {

    @Test
    public void returnConfirmedOrdinanceWithoutReasonWillFail() {
        Ordinance ordinance = formedOrdinanceSupplier.get();
        confirmFormeredOrdinance.accept(ordinance);
        returnConfirmedOrdinance.accept(null);
        //returnConfirmedOrdinance.accept(Pair.with(ordinance, ""));

    }

}
