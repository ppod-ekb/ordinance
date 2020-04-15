package ru.cbr.rrror.service.order.entity;

import lombok.*;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Immutable
public class OrdinanceStatus implements Persistable<Integer> {

    @Getter
    public enum Code {
        FORMED,
        CONFIRMED,
        RETURNED,
        APPROVED
    }

    @Id
    @Nullable
    private Integer id;
    private String name;
    private Code code;

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
