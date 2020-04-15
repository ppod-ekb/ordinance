package ru.cbr.rrror.service.order.command.registry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cbr.rrror.service.order.command.AbstractCommandTest;
import ru.cbr.rrror.service.order.entity.Registry;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
public class CreateRegistryIT extends AbstractCommandTest {

    @Test
    public void createRegistryWillSuccess() {
        Registry registry = createRegistry();

        assertNotNull(registry.getId());
        assertNotNull(registry.getCreationDate());
        assertNotNull(registry.getRegistryNum());
        assertTrue(registry.getEntries().isEmpty());
    }

}
