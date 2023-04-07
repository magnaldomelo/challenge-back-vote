package com.southsystem.challengebackvote.application.controller;

import com.southsystem.challengebackvote.domain.model.internal.request.SectionRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.SectionResponse;
import com.southsystem.challengebackvote.domain.service.SectionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@RequestMapping("/api/v1/section")
@AllArgsConstructor
@NoArgsConstructor
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SectionRequest sectionRequest) throws URISyntaxException {
        var response = this.sectionService.saveSection(sectionRequest);
        return created(new URI(response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        var response = this.sectionService.getAll().stream()
                .map(section -> modelMapper.map(section, SectionResponse.class))
                .collect(Collectors.toList());
        return ok(response);
    }

    @GetMapping("/{section_id}")
    public ResponseEntity<?> getById(@PathVariable("section_id") String id){
        var response = modelMapper.map(this.sectionService.getSectionById(id), SectionResponse.class);
        return ok(response);
    }

    @PutMapping("/{section_id}")
    public ResponseEntity<?> update(@PathVariable("section_id") String id, @RequestBody @Valid SectionRequest sectionRequest){
        var response = modelMapper.map(this.sectionService.update(id, sectionRequest), SectionResponse.class);
        return ok(response);
    }

    @PostMapping("/reset_expiration/{section_id}")
    public ResponseEntity<?> resetExpiration(@PathVariable("section_id") String id){
        this.sectionService.resetExpiration(id);
        return ok().build();
    }

    @DeleteMapping("/{section_id}")
    public ResponseEntity<Void> delete(@PathVariable("section_id") String id){
        this.sectionService.deleteById(id);
        return ok().build();
    }
}
