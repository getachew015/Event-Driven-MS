package com.ahuva.stock_service.service;

import com.ahuva.stock_service.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private StockRepository stockRepository;

    public void deductInventory(String itemId, int quantity) {

    }

    public void addInventory(String itemId, int quantity) {

    }

}
