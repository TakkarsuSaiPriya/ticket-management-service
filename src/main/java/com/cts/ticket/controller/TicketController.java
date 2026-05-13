package com.cts.ticket.controller;

import com.cts.ticket.dto.TicketResponseDTO;
import com.cts.ticket.service.TicketService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    // CREATE
    @PostMapping(consumes = "application/xml")
    public ResponseEntity<TicketResponseDTO> create(@RequestBody String xml) throws Exception {
        return ResponseEntity.ok(service.createTicket(xml));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTicket(id));
    }

    //  GET ALL
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllTickets());
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(
            @PathVariable Long id,
            @RequestBody TicketResponseDTO dto) {

        return ResponseEntity.ok(service.updateTicket(id, dto));
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}