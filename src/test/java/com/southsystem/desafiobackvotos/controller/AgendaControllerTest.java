package com.southsystem.desafiobackvotos.controller;

import com.southsystem.challengebackvote.application.controller.AgendaController;
import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;
import com.southsystem.challengebackvote.domain.model.internal.response.AgendaResponse;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AgendaControllerTest {

    @InjectMocks
    private AgendaController agendaController;

    @Mock
    private AgendaService agendaService;

    @Mock
    private ModelMapper modelMapper;

    private static final String AGENDA_NAME = "Teste1";
    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";

    private static Agenda agendaDataFake;
    private static AgendaRequest agendaRequestDataFake;
    private static AgendaResponse agendaResponseDataFake;


    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().name(AGENDA_NAME).id(AGENDA_ID).build();
        agendaRequestDataFake = AgendaRequest.builder().name(AGENDA_NAME).build();
        agendaResponseDataFake = AgendaResponse.builder().id(AGENDA_ID).name(AGENDA_NAME).build();
    }

    @Test
    public void shouldReturnRecordsAgenda(){
        List<Agenda> records = new ArrayList<>();
        records.add(new Agenda(AGENDA_ID, AGENDA_NAME));

        Mockito.when(agendaService.getAll()).thenReturn(records);
        ResponseEntity<?> response =  agendaController.getAll();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(1, ((ArrayList<?>) response.getBody()).size());
    }

    @Test
    public void createNewAgendaSuccess() throws URISyntaxException {
        Mockito.when(agendaService.saveAgenda(agendaRequestDataFake)).thenReturn(agendaDataFake);
        Mockito.when(modelMapper.map(agendaDataFake, AgendaResponse.class)).thenReturn(agendaResponseDataFake);
        ResponseEntity<?> response = agendaController.save(agendaRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
    }

    @Test
    public void shouldReturnRecordsByAgendaId(){
        Mockito.when(agendaService.getAgendaById(AGENDA_ID)).thenReturn(agendaDataFake);
        Mockito.when(modelMapper.map(agendaDataFake, AgendaResponse.class)).thenReturn(agendaResponseDataFake);
        ResponseEntity<?> response = agendaController.getById(AGENDA_ID);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(AGENDA_ID, agendaResponseDataFake.getId());
    }

    @Test
    public void updateAgendaSuccess(){
        var nameUpdated = AGENDA_NAME + "_updated";

        agendaDataFake.setName(nameUpdated);
        agendaResponseDataFake.setName(nameUpdated);

        Mockito.when(agendaService.update(AGENDA_ID, agendaRequestDataFake)).thenReturn(agendaDataFake);
        Mockito.when(modelMapper.map(agendaDataFake, AgendaResponse.class)).thenReturn(agendaResponseDataFake);
        ResponseEntity<?> response = agendaController.update(AGENDA_ID, agendaRequestDataFake);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(nameUpdated, ((AgendaResponse) response.getBody()).getName());
    }

    @Test
    public void shouldDeleteBuIdSuccess(){
        Mockito.doNothing().when(agendaService).deleteById(AGENDA_ID);
        ResponseEntity<?> response = agendaController.delete(AGENDA_ID);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
    }
}
