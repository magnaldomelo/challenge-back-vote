package com.southsystem.desafiobackvotos.service;

import com.southsystem.challengebackvote.domain.model.internal.Agenda;
import com.southsystem.challengebackvote.domain.model.internal.Section;
import com.southsystem.challengebackvote.domain.model.internal.request.SectionRequest;
import com.southsystem.challengebackvote.domain.service.AgendaService;
import com.southsystem.challengebackvote.domain.service.impl.SectionServiceImpl;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
import com.southsystem.challengebackvote.infrastructure.exception.EntityNotFoundException;
import com.southsystem.challengebackvote.infrastructure.repository.SectionRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SectionServiceTest {

    @Spy
    @InjectMocks
    private SectionServiceImpl sectionServiceImpl;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private AgendaService agendaService;

    private static final String AGENDA_NAME = "Teste1";
    private static final String AGENDA_ID = "642c1c51ff19cc787f8ec737";

    private static final String SECTION_ID = "642d87e1138a287f9833ecc6";
    private static final Boolean CLOUSED_TRUE = true;
    private static final Boolean CLOUSED_FALSE = false;
    private static final Integer MINUTES_TO_FINISH = 5;
    private static final Instant EXPIRATION = Instant.now(Clock.system(ZoneId.of("America/Sao_Paulo")));
    private static Agenda agendaDataFake;
    private static Section sectionDataFake;
    private static SectionRequest sectionRequestDataFake;

    @BeforeAll
    public static void setup(){
        agendaDataFake = Agenda.builder().id(AGENDA_ID).name(AGENDA_NAME).build();
        sectionRequestDataFake = SectionRequest.builder().cloused(CLOUSED_FALSE)
                .agendaId(AGENDA_ID).minutesToFinish(MINUTES_TO_FINISH).cloused(CLOUSED_FALSE).build();
        sectionDataFake = Section.builder().id(SECTION_ID).cloused(CLOUSED_FALSE).minutesToFinish(MINUTES_TO_FINISH)
                .agenda(agendaDataFake).expiration(EXPIRATION).build();
    }

    @Test
    public void shouldsaveSectionReturnSuccess(){
        List<Section> sections = new ArrayList<>();

        Mockito.when(agendaService.getAgendaById(AGENDA_ID)).thenReturn(agendaDataFake);
        Mockito.when(sectionRepository.findByAgendaAndClosed(new ObjectId(agendaDataFake.getId()), false))
                .thenReturn(sections);

        var section = sectionServiceImpl.saveSection(sectionRequestDataFake);

       Mockito.verify(sectionRepository).insert(any(Section.class));
    }

    @Test
    public void shouldsaveSectionReturnEntityNotFoundException(){
        Mockito.when(agendaService.getAgendaById(AGENDA_ID))
                .thenThrow(new EntityNotFoundException("Agenda for Id " + AGENDA_ID + " not Found."));

        Throwable throwable = assertThrows (EntityNotFoundException.class, () -> {
            var section = sectionServiceImpl.saveSection(sectionRequestDataFake);
        });

        assertEquals("Agenda for Id " + AGENDA_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldsaveSectionReturnBusinessException(){
        List<Section> sections = new ArrayList<>();
        sections.add(sectionDataFake);

        Mockito.when(agendaService.getAgendaById(AGENDA_ID)).thenReturn(agendaDataFake);
        Mockito.when(sectionRepository.findByAgendaAndClosed(new ObjectId(agendaDataFake.getId()), false))
                .thenReturn(sections);

        Throwable throwable = assertThrows (BusinessException.class, () -> {
            var section = sectionServiceImpl.saveSection(sectionRequestDataFake);
        });

        assertEquals("Section already exists for Agenda '" + AGENDA_NAME, throwable.getMessage());
    }

    @Test
    public void shouldgetSectionByIdReturnEntityNotFoundException(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows (EntityNotFoundException.class, () -> {
            var section = sectionServiceImpl.getSectionById(SECTION_ID);
        });

        assertEquals("Section for Id " + SECTION_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldSectionUpdateSuccess(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));
        Mockito.when(agendaService.getAgendaById(AGENDA_ID)).thenReturn(agendaDataFake);
        Mockito.when(sectionRepository.save(sectionDataFake)).thenReturn(sectionDataFake);

        sectionRequestDataFake.setCloused(CLOUSED_TRUE);
        var section = sectionServiceImpl.update(SECTION_ID, sectionRequestDataFake);

        assertEquals(sectionRequestDataFake.getCloused(), section.getCloused());
    }

    @Test
    public void shouldSectionUpdateReturnEntityNotFoundExceptionByBetAgendaById(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));
        Mockito.when(agendaService.getAgendaById(AGENDA_ID))
                .thenThrow(new EntityNotFoundException("Agenda for Id " + AGENDA_ID + " not Found."));
        Mockito.when(sectionRepository.save(sectionDataFake)).thenReturn(sectionDataFake);

        Throwable throwable = assertThrows (EntityNotFoundException.class, () -> {
            sectionRequestDataFake.setCloused(CLOUSED_TRUE);
            var section = sectionServiceImpl.update(SECTION_ID, sectionRequestDataFake);
        });

        assertEquals("Agenda for Id " + AGENDA_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldSectionUpdateReturnEntityNotFoundExceptionByGetSectionById(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.empty());
        Mockito.when(agendaService.getAgendaById(AGENDA_ID)).thenReturn(agendaDataFake);
        Mockito.when(sectionRepository.save(sectionDataFake)).thenReturn(sectionDataFake);

        Throwable throwable = assertThrows (EntityNotFoundException.class, () -> {
            sectionRequestDataFake.setCloused(CLOUSED_TRUE);
            var section = sectionServiceImpl.update(SECTION_ID, sectionRequestDataFake);
        });

        assertEquals("Section for Id " + SECTION_ID + " not Found.", throwable.getMessage());
    }

    @Test
    public void shouldgetSectionDeleteByIdSuccess(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.of(sectionDataFake));
        sectionServiceImpl.deleteById(SECTION_ID);
        Mockito.verify(sectionRepository).deleteById(any());
    }

    @Test
    public void shouldgetSectionDeleteByIdReturnEntityNotFoundException(){
        Mockito.when(sectionRepository.findById(SECTION_ID)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows (EntityNotFoundException.class, () -> {
            sectionServiceImpl.deleteById(SECTION_ID);
        });

        assertEquals("Section for Id " + SECTION_ID + " not Found.", throwable.getMessage());
    }
}
