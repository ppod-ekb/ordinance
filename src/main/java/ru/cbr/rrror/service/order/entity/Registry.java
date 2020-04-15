package ru.cbr.rrror.service.order.entity;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
public class Registry extends AbstractPersistable<Long> {

    public String registryNum;

    public LocalDate creationDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RegistryEntry> entries = new HashSet<>();

    public void addEntry(RegistryEntry entry) {
        entries.add(entry);
    }

}
