package ru.cbr.rrror.service.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ExecutionDetail implements Persistable<Long> {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    private String regNum;
    private LocalDate regDate;

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
