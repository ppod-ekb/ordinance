package ru.cbr.rrror.service.order.command.ordinance;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.exception.IllegalOrdinanceStatusException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
public class ConfirmFormeredOrdinanceIT extends AbstractCommandTest {

    @Test
    public void confirmFormeredOrdinanceWillSuccess() {
        Ordinance ordinance = formedOrdinanceSupplier.get();

        assertNull(ordinance.getOrderSpec());

        confirmFormeredOrdinance.accept(ordinance);

        assertTrue(ordinance.getOrdinanceStatus().getCode().equals(OrdinanceStatus.Code.CONFIRMED));

        log.debug(">>> " + ordinance.toString());
    }

    @Test
    public void confirmNonFormeredOrdinanceWillFail() {
        Ordinance ordinance = formedOrdinanceSupplier.get();

        confirmFormeredOrdinance.accept(ordinance);

        assertTrue(ordinance.getOrdinanceStatus().getCode().equals(OrdinanceStatus.Code.CONFIRMED));

        Executable confirmConfirmedOrdinance = () -> confirmFormeredOrdinance.accept(ordinance);

        assertThrows(IllegalOrdinanceStatusException.class, confirmConfirmedOrdinance);
    }


}
