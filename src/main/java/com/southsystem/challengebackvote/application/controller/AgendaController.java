package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.AgendaResponse;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("agenda")
@Api(value = "agenda")
@AllArgsConstructor
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value="create one agenda", response = AgendaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Agenda successfully created.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AgendaRequest agendaRequest) throws URISyntaxException {
        var response =  modelMapper.map(agendaService.saveAgenda(agendaRequest), AgendaResponse.class);
        return created(new URI(response.getId())).body(response);
    }

    @ApiOperation(value="get all agendas", response = AgendaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Agendas found.")
    })
    @GetMapping
    public ResponseEntity<?> getAll(){
        var response = agendaService.getAll().stream().map(
                agenda -> modelMapper.map(agenda, AgendaResponse.class)
        ).collect(Collectors.toList());
        return ok(response);
    }

    @ApiOperation(value="get all agendas", response = AgendaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Agendas found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        var response = modelMapper.map(agendaService.getAgendaById(id), AgendaResponse.class);
        return ok(response);
    }

    @ApiOperation(value="get all agendas", response = AgendaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Agendas found.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody @Valid AgendaRequest agendaRequest){
        var response = modelMapper.map(agendaService.update(id, agendaRequest), AgendaResponse.class);
        return ok(response);
    }

    @ApiOperation(value="get all agendas", response = AgendaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Agendas found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        agendaService.deleteById(id);
        return ok().build();
    }
}
