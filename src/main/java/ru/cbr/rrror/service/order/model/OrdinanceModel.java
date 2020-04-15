package ru.cbr.rrror.service.order.model;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import ru.cbr.rrror.service.order.entity.Ordinance;

@Getter
public class OrdinanceModel extends RepresentationModel<OrdinanceModel> {

    private final Long id;
    private final String status;

    public OrdinanceModel(Ordinance ordinance) {
        this.id = ordinance.getId();
        this.status = ordinance.getOrdinanceStatus().getName();
    }
}
