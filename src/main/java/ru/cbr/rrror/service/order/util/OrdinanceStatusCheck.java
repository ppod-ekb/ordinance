package ru.cbr.rrror.service.order.util;

import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.entity.OrdinanceStatus;
import ru.cbr.rrror.service.order.exception.IllegalOrdinanceStatusException;

import java.util.Arrays;
import java.util.List;

public class OrdinanceStatusCheck {
    private final List<OrdinanceStatus.Code> allowedStats;

    public OrdinanceStatusCheck(OrdinanceStatus.Code... codes) {
        this.allowedStats = Arrays.asList(codes);
    }

    public boolean check(Ordinance ordinance) {
        return allowedStats.contains(ordinance.getOrdinanceStatus().getCode());
    }

    @Deprecated
    public void checkOrError(Ordinance ordinance, String message) {
        if (!check(ordinance)) {
            throw new IllegalOrdinanceStatusException(message);
        }
    }
}
