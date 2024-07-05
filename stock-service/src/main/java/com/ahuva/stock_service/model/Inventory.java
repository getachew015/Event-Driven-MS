package com.ahuva.stock_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    private String itemId;
    private String itemName;
    private int stockQuantity;
    private String remark;
    private Date lastUpdateTimeStamp;
}
