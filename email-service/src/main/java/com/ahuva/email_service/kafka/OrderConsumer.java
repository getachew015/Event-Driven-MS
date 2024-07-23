package com.ahuva.email_service.kafka;

import com.ahuva.base_domain.dto.OrderEvent;
import com.ahuva.email_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class OrderConsumer {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Order event received in stock-api {} at {}", orderEvent, new Date());
        try {
            //Send Email To Sales Team
            emailService.sendMessage(orderEvent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return today.format(formatter);
    }
}
