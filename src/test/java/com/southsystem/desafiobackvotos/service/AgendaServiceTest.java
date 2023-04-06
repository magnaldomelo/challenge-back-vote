package com.southsystem.desafiobackvotos.service;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.request.AgendaRequest;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import com.southsystem.challengebackvote.domain.service.impl.AgendaServiceImpl;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.exception.EntityNotFoundException;
import com.southsystem.challengebackvote.infrastructure.repository.AgendaRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    private AgendaServiceImpl agendaServiceImpl;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ModelMapper modelMapper;

    private static final String AGENDA_NAME = "Teste1";
    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";
    private static Agenda agendaDataFake;
    private static AgendaRequest agendaRequestDataFake;

    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().name(AGENDA_NAME).id(AGENDA_ID).build();
        agendaRequestDataFake = AgendaRequest.builder().name(AGENDA_NAME).build();
    }

    @Test
    public void saveNewAgendaSuccess(){
        Mockito.when(agendaRepository.findByName(AGENDA_NAME)).thenReturn(new ArrayList<>());
        Mockito.when(agendaRepository.insert(modelMapper.map(agendaRequestDataFake, Agenda.class)))
                .thenReturn(agendaDataFake);

        var agendaByService = agendaServiceImpl.saveAgenda(agendaRequestDataFake);

        assertEquals(AGENDA_ID, agendaByService.getId());
    }

    @Test
    public void saveNewAgendaReturnBusinessException(){
        List<Agenda> agendas = new ArrayList<>();
        agendas.add(agendaDataFake);
        Mockito.when(agendaRepository.findByName(AGENDA_NAME)).thenReturn(agendas);

        Throwable throwable = assertThrows(BusinessException.class, () -> {
            var agendaByService = agendaServiceImpl.saveAgenda(agendaRequestDataFake);
        });

        assertEquals("Reported record '" + AGENDA_NAME + "' already exists.", throwable.getMessage());
    }

    @Test
    public void shouldGetAllReturnListForAgenda(){
        List<Agenda> agendas = new ArrayList<>();
        var nameUpdated = AGENDA_NAME + "_updated";

        agendas.add(agendaDataFake);
        agendas.add(Agenda.builder().name(nameUpdated).id(new ObjectId().toHexString()).build());

        Mockito.when(agendaRepository.findAll()).thenReturn(agendas);
        var agendaSearch = agendaServiceImpl.getAll();

        assertEquals(agendas.size(), agendaSearch.size());
    }

    @Test
    public void shouldGetAllReturnList(){
        List<Agenda> agendas = new ArrayList<>();

        Mockito.when(agendaRepository.findAll()).thenReturn(agendas);

        var agendaSearch = agendaServiceImpl.getAll();

        assertEquals(agendas.isEmpty(), agendaSearch.isEmpty());
    }

    @Test
    public void shouldgetAgendaByIdReturnAgenda(){
        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agendaDataFake));
        var agenda = agendaServiceImpl.getAgendaById(AGENDA_ID);

        assertEquals(AGENDA_ID, agenda.getId());
    }

    @Test
    public void shouldgetAgendaByIdReturnEmpty(){
        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> {
            var agenda = agendaServiceImpl.getAgendaById(AGENDA_ID);
        });

        assertEquals("Agenda for Id " + AGENDA_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldgetAgendaUpdateSuccess(){
        var nameUpdated = AGENDA_NAME + "_updated";
        var agendaUpdated = Agenda.builder().name(nameUpdated).id(new ObjectId().toHexString()).build();

        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agendaDataFake));
        Mockito.when(agendaRepository.save(agendaDataFake)).thenReturn(agendaUpdated);
        var agenda = agendaServiceImpl.update(AGENDA_ID, agendaRequestDataFake);

        assertEquals(nameUpdated, agenda.getName());
    }

    @Test
    public void shouldgetAgendaUpdateReturnEntityNotFoundException(){
        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> {
            var agenda = agendaServiceImpl.update(AGENDA_ID, agendaRequestDataFake);
        });

        assertEquals("Agenda for Id " + AGENDA_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldgetAgendaDeleteByIdSuccess(){
        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agendaDataFake));
        agendaServiceImpl.deleteById(AGENDA_ID);
        Mockito.verify(agendaRepository).deleteById(any());
    }

    @Test
    public void shouldgetAgendaDeleteByIdEntityNotFoundException(){
        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(EntityNotFoundException.class, () -> {
            agendaServiceImpl.deleteById(AGENDA_ID);
        });

        assertEquals("Agenda for Id " + AGENDA_ID + " not Found.", throwable.getMessage());
    }
}
