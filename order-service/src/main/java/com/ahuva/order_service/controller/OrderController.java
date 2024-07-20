package com.ahuva.order_service.controller;

import com.ahuva.base_domain.dto.Order;
import com.ahuva.base_domain.dto.OrderEvent;
import com.ahuva.order_service.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("orders-api/v1")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping(path = "/orders", produces = "application/json")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderDate(getDate());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order is in Pending status " + new Date());
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return new ResponseEntity<>("Order was successfully created", CREATED);
    }

    private String getDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return today.format(formatter);
    }
}
