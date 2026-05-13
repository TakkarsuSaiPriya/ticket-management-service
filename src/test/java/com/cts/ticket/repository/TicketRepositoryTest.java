package com.cts.ticket.repository;

import com.cts.ticket.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository repo;

    @Test
    void testSave() {
        Ticket t = new Ticket();
        t.setCustomerId(1L);

        Ticket saved = repo.save(t);

        assertNotNull(saved.getId());
    }
}