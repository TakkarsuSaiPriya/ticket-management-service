package com.cts.ticket.service;

import com.cts.ticket.dto.TicketRequestDTO;
import com.cts.ticket.dto.TicketResponseDTO;
import com.cts.ticket.entity.Ticket;
import com.cts.ticket.entity.TicketAuditLog;
import com.cts.ticket.mapper.TicketMapper;
import com.cts.ticket.messaging.MessageProducer;
import com.cts.ticket.repository.AuditRepository;
import com.cts.ticket.repository.TicketRepository;
import com.cts.ticket.util.XmlValidator;

import jakarta.xml.bind.JAXBContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repo;
    private final TicketMapper mapper;
    private final MessageProducer producer;
    private final AuditRepository auditRepo;

    //  CREATE
    public TicketResponseDTO createTicket(String xml) throws Exception {

        XmlValidator.validate(xml);

        JAXBContext context = JAXBContext.newInstance(TicketRequestDTO.class);
        TicketRequestDTO dto = (TicketRequestDTO)
                context.createUnmarshaller()
                        .unmarshal(new StringReader(xml));

        Ticket ticket = mapper.toEntity(dto);

        // APPLY BUSINESS LOGIC AFTER MAPPING
        if ("TECHNICAL".equals(dto.getIssueType())) {
            ticket.setPriority("MEDIUM");
        }

        ticket.setSubmittedAt(LocalDateTime.parse(dto.getSubmittedAt()));

        repo.save(ticket);

        producer.sendWithRetry(xml);

        saveAudit(ticket.getId(), "CREATE");

        return mapper.toDTO(ticket);
    }

    // GET BY ID
    public TicketResponseDTO getTicket(Long id) {
        Ticket t = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return mapper.toDTO(t);
    }

    // GET ALL
    public List<TicketResponseDTO> getAllTickets() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE (FULL FIX)
    public TicketResponseDTO updateTicket(Long id, TicketResponseDTO dto) {

        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        //  UPDATE ALL FIELDS
        ticket.setPriority(dto.getPriority());
        ticket.setStatus(dto.getStatus());
        ticket.setDescription(dto.getDescription());  

        repo.save(ticket);

        saveAudit(id, "UPDATE");

        return mapper.toDTO(ticket);
    }

    //  DELETE
    public void deleteTicket(Long id) {

        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        repo.delete(ticket);

        saveAudit(id, "DELETE");
    }

    // AUDIT
    private void saveAudit(Long id, String action) {
        TicketAuditLog log = new TicketAuditLog();
        log.setTicketId(id);
        log.setAction(action);
        log.setSource("REST");

        auditRepo.save(log);
    }
}
