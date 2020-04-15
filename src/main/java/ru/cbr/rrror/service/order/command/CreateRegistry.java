package ru.cbr.rrror.service.order.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.entity.Registry;
import ru.cbr.rrror.service.order.repository.RegistryRepository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class CreateRegistry implements Consumer<Registry> {

    @Autowired
    private RegistryRepository repository;

    private RegistryNumGenerator registryNumGenerator = new RegistryNumGenerator();

    @Override
    public void accept(Registry registry) {
        repository.save(createRegistry(registry));
    }

    private Registry createRegistry(Registry registry) {
        registry.setCreationDate(LocalDate.now());
        registry.setRegistryNum(registryNumGenerator.get());

        return registry;
    }

    static class RegistryNumGenerator implements Supplier<String> {

        @Override
        public String get() {
            return UUID.randomUUID().toString();
        }
    }
}
