package com.cts.ticket.service;

import com.cts.ticket.dto.TicketResponseDTO;
import com.cts.ticket.entity.Ticket;
import com.cts.ticket.mapper.TicketMapper;
import com.cts.ticket.messaging.MessageProducer;
import com.cts.ticket.repository.AuditRepository;
import com.cts.ticket.repository.TicketRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repo;

    @Mock
    private TicketMapper mapper;

    @Mock
    private MessageProducer producer;

    @Mock
    private AuditRepository auditRepo;

    @InjectMocks
    private TicketService service;

    private Ticket ticket;
    private TicketResponseDTO dto;

    @BeforeEach
    void setup() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPriority("HIGH");
        ticket.setStatus("OPEN");
        ticket.setDescription("Test ticket");

        dto = new TicketResponseDTO();
        dto.setId(1L);
        dto.setPriority("HIGH");
        dto.setStatus("OPEN");
        dto.setDescription("Test ticket");
    }

    @Test
    void testGetTicket() {

        when(repo.findById(1L)).thenReturn(Optional.of(ticket));
        when(mapper.toDTO(ticket)).thenReturn(dto);

        TicketResponseDTO result = service.getTicket(1L);

        assertNotNull(result);
        assertEquals("HIGH", result.getPriority());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testUpdateTicket() {

        when(repo.findById(1L)).thenReturn(Optional.of(ticket));
        when(repo.save(any(Ticket.class))).thenReturn(ticket);
        when(mapper.toDTO(ticket)).thenReturn(dto);

        TicketResponseDTO result = service.updateTicket(1L, dto);

        assertEquals("OPEN", result.getStatus());
        verify(repo, times(1)).save(ticket);
    }

    @Test
    void testDeleteTicket() {

        when(repo.findById(1L)).thenReturn(Optional.of(ticket));

        service.deleteTicket(1L);

        verify(repo, times(1)).delete(ticket);
    }
}