package com.ahuva.stock_service.service;

import com.ahuva.stock_service.model.Inventory;
import com.ahuva.stock_service.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private StockRepository stockRepository;

    public void deductInventory(String itemId, int quantity) {
        Optional<Inventory> inventoryOptional = stockRepository.findById(itemId);
        Inventory inventory;
        if (inventoryOptional.isPresent()) {
            inventory = inventoryOptional.get();
            int currentStock = inventory.getStockQuantity() - quantity;
            if (currentStock < 0) {
                inventory.setRemark("Inventory is low and need to re stock item.");
                log.error("order is not processed because of low inventory");
            } else {
                inventory.setStockQuantity(currentStock);
                inventory.setLastUpdateTimeStamp(new Date());
                stockRepository.save(inventory);
                log.info("Inventory deducted successfully for item {} ...", itemId);
            }
        }

    }

    public Inventory addInventory(String itemId, int quantity) {
        Optional<Inventory> inventoryOptional = stockRepository.findById(itemId);
        Inventory inventory = null;
        if (inventoryOptional.isPresent()) {
            inventory = inventoryOptional.get();
            inventory.setStockQuantity(inventory.getStockQuantity() + quantity);
            inventory.setLastUpdateTimeStamp(new Date());
            stockRepository.save(inventory);
        }
        return inventory;
    }

    public List<Inventory> getItems() {
        return stockRepository.findAll();
    }

}
