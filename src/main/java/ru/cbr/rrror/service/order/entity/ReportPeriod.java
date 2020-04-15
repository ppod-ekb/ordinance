package ru.cbr.rrror.service.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ReportPeriod implements Persistable<Integer> {

    @Id
    private Integer id;
    private String periodName;
    private String monthName;
    private String yearName;
    private Integer month;
    private Integer year;

    @Override
    public boolean isNew() {
        return this.getId() == null;
    }
}
