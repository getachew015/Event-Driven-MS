package com.ahuva.base_domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String itemId;
    private String itemName;
    private int quantity;
    private double price;
}
