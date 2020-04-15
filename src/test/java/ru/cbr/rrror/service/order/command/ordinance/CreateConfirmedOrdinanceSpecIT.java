package ru.cbr.rrror.service.order.command.ordinance;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Ordinance;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@Transactional
public class CreateConfirmedOrdinanceSpecIT extends AbstractCommandTest {

    @Test
    public void createConfirmedIOrdinanceSpecWillSuccess() {
        Ordinance ordinance = formedOrdinanceSupplier.get();

        confirmFormeredOrdinance
                .andThen(createConfirmedOrdinanceSpec)
                .accept(ordinance);

        assertNotNull(ordinance.getOrderSpec());
        assertNotNull(ordinance.getOrderSpec().getNum());
        assertNotNull(ordinance.getOrderSpec().getCreationDate());


    }
}
