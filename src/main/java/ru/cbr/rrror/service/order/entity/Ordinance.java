package ru.cbr.rrror.service.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
public class Ordinance extends AbstractPersistable<Long> {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CO_ID")
    private CreditOrganization creditOrganization;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TU_ID")
    private TuCbr tuCbr;

    @ManyToOne
    @JoinColumn(name = "ORDINANCE_STATUS_ID")
    private OrdinanceStatus ordinanceStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FUND_STATUS_ID")
    private FundStatus fundStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REPORT_PERIOD_ID")
    private ReportPeriod reportPeriod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXECUTION_DETAIL_ID", referencedColumnName = "ID")
    private ExecutionDetail executionDetail;

    @Embedded
    private CalcSpec calcSpec;

    @Embedded
    private OrderSpec orderSpec;

    @Embedded
    private RegistrySpec registrySpec;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordinance", fetch = FetchType.LAZY)
    private Set<OrdinanceStatusHistory> ordinanceStatusHistories = new HashSet<>();

    private String reason;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class OrderSpec {

        @Column(nullable = true)
        private String num;

        @Column(nullable = true)
        private LocalDate creationDate;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RegistrySpec {

        public RegistrySpec(Registry registry) {
            this.registryId = registry.getId();
            this.registryNum = registry.getRegistryNum();
            this.name = registryNum + " от " + registry.getCreationDate().toString();
        }

        private Long registryId;
        private String registryNum;
        private String name;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CalcSpec {

        @Column(name = "CALC_ID", nullable = false)
        private Long calcId;

        @Column(name = "VALUE", nullable = false, precision = 18, scale = 2)
        private BigDecimal value;

        @Column(name = "CALC_VALUE", nullable = false, precision = 18, scale = 2)
        private BigDecimal calcValue;

        @Column(name = "FACT_VALUE", nullable = false, precision = 18, scale = 2)
        private BigDecimal factValue;
    }

}

