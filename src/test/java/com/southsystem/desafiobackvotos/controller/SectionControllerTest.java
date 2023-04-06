package com.southsystem.desafiobackvotos.controller;

import com.southsystem.challengebackvote.application.controller.SectionController;
import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.request.SectionRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.SectionResponse;
import com.southsystem.challengebackvote.domain.service.SectionService;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;

import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SectionControllerTest {

    @InjectMocks
    private SectionController sectionController;

    @Mock
    private SectionService sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ModelMapper modelMapper;

    private static final String SECTION_ID = "642c2478e4c90845bca545c7";
    private static final String AGENDA_NAME = "Teste1";
    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";
    private static final Boolean CLOUSED_TRUE = true;
    private static final Boolean CLOUSED_FALSE = false;
    private static final Integer MINUTES_TO_FINISH = 5;
    private static final Instant EXPIRATION = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
    private static Agenda agendaDataFake;
    private static Section sectionDataFake;
    private static SectionRequest sectionRequestDataFake;
    private static SectionResponse sectionResponseDataFake;

    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().name(AGENDA_NAME).id(AGENDA_ID).build();
        sectionRequestDataFake = SectionRequest.builder().cloused(CLOUSED_FALSE)
                .agendaId(AGENDA_ID).minutesToFinish(MINUTES_TO_FINISH).cloused(CLOUSED_FALSE).build();
        sectionDataFake = Section.builder().agenda(agendaDataFake).expiration(EXPIRATION).build();
        sectionResponseDataFake = SectionResponse.builder().id(SECTION_ID).minutesToFinish(MINUTES_TO_FINISH)
                .cloused(CLOUSED_FALSE).expiration(EXPIRATION).agenda(agendaDataFake).build();
    }

    @Test
    public void shouldReturnRecordsSection(){
        List<Section> records = new ArrayList<>();
        records.add(Section.builder().agenda(agendaDataFake).cloused(CLOUSED_FALSE)
                .minutesToFinish(MINUTES_TO_FINISH).expiration(EXPIRATION).build());

        Mockito.when(sectionService.getAll()).thenReturn(records);
        ResponseEntity<?> response =  sectionController.getAll();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) response.getBody()).size());
    }

    @Test
    public void createNewSectionSuccess() throws URISyntaxException {
        sectionDataFake.setId(new ObjectId().toHexString());
        Mockito.when(sectionService.saveSection(sectionRequestDataFake)).thenReturn(sectionDataFake);
        ResponseEntity<?> response = sectionController.save(sectionRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertEquals(AGENDA_ID, ((Section) response.getBody()).getAgenda().getId());
    }

    @Test
    public void shouldReturnRecordsBySectionId(){
        Mockito.when(sectionService.getSectionById(SECTION_ID)).thenReturn(sectionDataFake);
        Mockito.when(modelMapper.map(sectionDataFake, SectionResponse.class)).thenReturn(sectionResponseDataFake);
        ResponseEntity<?> response = sectionController.getById(SECTION_ID);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(SECTION_ID, ((SectionResponse) response.getBody()).getId());
    }

    @Test
    public void updateSectionSuccess(){
        sectionRequestDataFake.setCloused(CLOUSED_TRUE);
        sectionResponseDataFake.setCloused(CLOUSED_TRUE);

        Mockito.when(sectionService.update(SECTION_ID, sectionRequestDataFake)).thenReturn(sectionDataFake);
        Mockito.when(modelMapper.map(sectionDataFake, SectionResponse.class)).thenReturn(sectionResponseDataFake);
        ResponseEntity<?> response = sectionController.update(SECTION_ID, sectionRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(CLOUSED_TRUE, ((SectionResponse) response.getBody()).getCloused());
        assertEquals(sectionResponseDataFake.getExpiration(), ((SectionResponse) response.getBody()).getExpiration());
    }

    @Test
    public void shouldDeleteBuIdSuccess(){
        Mockito.when(sectionService.update(SECTION_ID, sectionRequestDataFake)).thenReturn(sectionDataFake);
        ResponseEntity<?> response = sectionController.update(SECTION_ID, sectionRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }
}
