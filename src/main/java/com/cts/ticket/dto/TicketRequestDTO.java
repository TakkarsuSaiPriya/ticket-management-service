package com.cts.ticket.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "Ticket")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class TicketRequestDTO {

    private Long customerId;
    private String issueType;
    private String priority;
    private String description;

    // ✅ MUST be String (not LocalDateTime)
    private String submittedAt;
}