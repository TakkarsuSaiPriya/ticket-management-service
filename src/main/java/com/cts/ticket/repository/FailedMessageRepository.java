package com.cts.ticket.repository;

import com.cts.ticket.entity.FailedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedMessageRepository extends JpaRepository<FailedMessage, Long> {}
