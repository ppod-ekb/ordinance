package ru.cbr.rrror.service.order.assembler;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.controller.OrdinanceController;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.model.OrdinanceModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class OrdinanceModelAssembler extends RepresentationModelAssemblerSupport<Ordinance, OrdinanceModel> {

    public OrdinanceModelAssembler() {
        super(OrdinanceController.class, OrdinanceModel.class);
    }

    @Override
    public OrdinanceModel toModel(Ordinance entity) {
        return new OrdinanceModel(entity)
                .add(linkTo(methodOn(OrdinanceController.class).confirm(entity.getId())).withRel("confirm"))
                .add(selfLink(entity.getId()));
    }

    public Link selfLink(Long id) {
        return linkTo(methodOn(OrdinanceController.class).get(id)).withSelfRel();
    }
}
