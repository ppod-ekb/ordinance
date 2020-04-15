package ru.cbr.rrror.service.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class TuCbr implements Persistable<Integer> {

    @Id
    @Nullable
    private Integer id;
    private String code;
    private String name;
    private String shortName;

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
