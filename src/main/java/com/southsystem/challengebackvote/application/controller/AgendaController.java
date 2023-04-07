package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.AgendaResponse;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/v1/agenda")
@Api(value = "agenda")
@AllArgsConstructor
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AgendaRequest agendaRequest) throws URISyntaxException {
        var response =  modelMapper.map(agendaService.saveAgenda(agendaRequest), AgendaResponse.class);
        return created(new URI(response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        var response = agendaService.getAll().stream().map(
                agenda -> modelMapper.map(agenda, AgendaResponse.class)
        ).collect(Collectors.toList());
        return ok(response);
    }

    @GetMapping("/{agenda_id}")
    public ResponseEntity<?> getById(@PathVariable("agenda_id") String id){
        var response = modelMapper.map(agendaService.getAgendaById(id), AgendaResponse.class);
        return ok(response);
    }

    @PutMapping("/{agenda_id}")
    public ResponseEntity<?> update(@PathVariable("agenda_id") String id, @RequestBody @Valid AgendaRequest agendaRequest){
        var response = modelMapper.map(agendaService.update(id, agendaRequest), AgendaResponse.class);
        return ok(response);
    }

    @DeleteMapping("/{agenda_id}")
    public ResponseEntity<Void> delete(@PathVariable("agenda_id") String id){
        agendaService.deleteById(id);
        return ok().build();
    }
}
