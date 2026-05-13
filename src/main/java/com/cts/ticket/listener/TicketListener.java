package com.cts.ticket.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TicketListener {

    @JmsListener(destination = "ticket.events")
    public void listen(String message) {

    
        System.out.println("✅ ✅ RECEIVED MESSAGE FROM QUEUE:");
        System.out.println(message);

    }
}
