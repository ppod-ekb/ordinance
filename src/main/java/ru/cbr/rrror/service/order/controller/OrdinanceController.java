package ru.cbr.rrror.service.order.controller;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.cbr.rrror.service.order.assembler.OrdinanceModelAssembler;
import ru.cbr.rrror.service.order.entity.Ordinance;
import ru.cbr.rrror.service.order.exception.OrdinanceNotFoundException;
import ru.cbr.rrror.service.order.model.OrdinanceModel;
import ru.cbr.rrror.service.order.service.OrdinanceReaderService;
import ru.cbr.rrror.service.order.service.OrdinanceWriterService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@RestController
public class OrdinanceController {

    @Autowired
    private OrdinanceWriterService writerService;

    @Autowired
    private OrdinanceReaderService readerService;

    @Autowired
    private OrdinanceModelAssembler assembler;

    private Function<Long, Ordinance> getOrdinanceById = (Long id) -> readerService.byId(id);
    private Consumer<Ordinance> addOrdinanceStatusToHistory = (Ordinance o) -> writerService.addOrdinanceStatusToHistory(o);

    @GetMapping("/ordinance/{id}")
    public ResponseEntity<OrdinanceModel> get(@PathVariable Long id) {
        return getOrdinanceById
                .andThen(assembler::toModel)
                .andThen(ResponseEntity::ok)
                .apply(id);
    }

    @PostMapping("/ordinance/{id}/confirm")
    public ResponseEntity<Link> confirm(@PathVariable Long id) {
        Consumer<Ordinance> confirmAndCreateOrdinanceSpec = (Ordinance o) -> writerService.confirmAndCreateOrdinanceSpec(o);

        confirmAndCreateOrdinanceSpec
                .andThen(addOrdinanceStatusToHistory)
                .accept(getOrdinanceById.apply(id));

        return ResponseEntity.ok(assembler.selfLink(id));
    }

    @PostMapping("/ordinance/{id}/return")
    public ResponseEntity<Link> returnConfirmed(@PathVariable Long id, @RequestParam String reason) {
        Consumer<Pair<Ordinance, String>> returnConfirmedOrdinance = (Pair<Ordinance, String> v) -> writerService.returnConfirmedOrdinance(v);
        Consumer<Pair<Ordinance, String>> addToHistory = (Pair<Ordinance, String> v) -> addOrdinanceStatusToHistory.accept(v.getValue0());

        returnConfirmedOrdinance
                .andThen(addToHistory)
                .accept(getOrdinanceById
                        .andThen(o -> Pair.with(o,reason))
                        .apply(id));

        return ResponseEntity.ok(assembler.selfLink(id));
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalStateException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

/*
    @ControllerAdvice
    class RuntimeExceptionAdvice {

        @ResponseBody
        @ExceptionHandler(IllegalStateException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        String runtimeException(IllegalStateException ex) {
             return ex.getMessage();
        }
    }

    @ControllerAdvice
    class OrdinanceNotFoundExceptionAdvice {

        @ResponseBody
        @ExceptionHandler(OrdinanceNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String ordinanceNotFoundHandler(OrdinanceNotFoundException ex) {
            return ex.getMessage();
        }
    }
*/
}
