package ru.cbr.rrror.service.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class OrdinanceStatusHistory extends AbstractPersistable<Long> {

    public OrdinanceStatusHistory(Ordinance ordinance) {
        this.ordinanceStatus = ordinance.getOrdinanceStatus();
        this.reason = ordinance.getReason();
        this.localDateTime = LocalDateTime.now();
        this.ordinance = ordinance;
    }

    @ManyToOne
    @JoinColumn(name = "ORDINANCE_STATUS_ID")
    private OrdinanceStatus ordinanceStatus;

    private String reason;

    private LocalDateTime localDateTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDINANCE_ID")
    private Ordinance ordinance;
}
