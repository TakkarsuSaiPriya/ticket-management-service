package com.cts.ticket.repository;

import com.cts.ticket.entity.TicketAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<TicketAuditLog, Long> {}