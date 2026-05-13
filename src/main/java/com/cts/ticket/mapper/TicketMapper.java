package com.cts.ticket.mapper;

import com.cts.ticket.dto.TicketRequestDTO;
import com.cts.ticket.dto.TicketResponseDTO;
import com.cts.ticket.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketRequestDTO dto);

    TicketResponseDTO toDTO(Ticket entity);
}