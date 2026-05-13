package com.cts.ticket.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "support_tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String issueType;
    private String priority;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "OPEN";
    private LocalDateTime submittedAt;
}
