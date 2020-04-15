package ru.cbr.rrror.service.order.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class RegistryEntry extends AbstractPersistable<Long> {

    public RegistryEntry(Ordinance ordinance) {
        this.tuCbr = ordinance.getTuCbr();
        this.creditOrganization = ordinance.getCreditOrganization();
        this.fundStatus = ordinance.getFundStatus();
        this.value = ordinance.getCalcSpec().getValue();
    }

    private BigDecimal value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CO_ID")
    private CreditOrganization creditOrganization;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TU_ID")
    private TuCbr tuCbr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FUND_STATUS_ID")
    private FundStatus fundStatus;
}
