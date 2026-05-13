package com.cts.ticket.messaging;

import com.cts.ticket.entity.FailedMessage;
import com.cts.ticket.repository.FailedMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final JmsTemplate jmsTemplate;
    private final FailedMessageRepository failedRepo;

    public void sendWithRetry(String msg) {

        int retry = 0;

        while (retry < 3) {
            try {
                jmsTemplate.convertAndSend("ticket.events", msg);
                System.out.println("✅ Message sent successfully to queue: ticket.events");
                return;
            } catch (Exception e) {
                retry++;
                System.out.println("❌ Attempt " + retry + " failed: " + e.getMessage());

                if (retry == 3) {
                    System.out.println("🚨 All 3 retries exhausted! Saving failed message to DB...");

                    FailedMessage fm = new FailedMessage();
                    fm.setPayload(msg);
                    fm.setErrorMessage(e.getMessage());
                    fm.setQueueName("ticket.events");
                    fm.setRetryCount(retry);

                    failedRepo.save(fm);
                    System.out.println("💾 Failed message saved to DB successfully.");
                }
            }
        }
    }
}