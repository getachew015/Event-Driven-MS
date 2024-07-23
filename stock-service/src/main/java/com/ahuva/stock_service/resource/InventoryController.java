package com.ahuva.stock_service.resource;

import com.ahuva.stock_service.model.Inventory;
import com.ahuva.stock_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("stock-api/v1")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/items", produces = "application/json")
    public ResponseEntity<List<Inventory>> getAllItems() {

        return new ResponseEntity<>(inventoryService.getItems(), OK);
    }

    @GetMapping(path = "/items/{id}/{quantity}", produces = "application/json")
    public ResponseEntity<Inventory> addItem(@PathVariable("id") String itemId, @PathVariable("quantity") int stockQuantity) {
        Inventory inventory = inventoryService.addInventory(itemId, stockQuantity);
        return new ResponseEntity<>(inventory, OK);
    }

}
