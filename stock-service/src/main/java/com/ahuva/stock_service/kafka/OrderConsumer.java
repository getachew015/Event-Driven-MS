package com.ahuva.stock_service.kafka;

import com.ahuva.base_domain.dto.OrderEvent;
import com.ahuva.stock_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class OrderConsumer {
    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Order event received in stock-api {} at {}", orderEvent, new Date());
        //persist order to Stock database for inventory calc
        orderEvent.getOrder().getItemList().forEach(item -> inventoryService.deductInventory(
                item.getItemId(), item.getQuantity()));
    }
}
