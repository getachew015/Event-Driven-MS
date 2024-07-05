package com.ahuva.stock_service.repository;

import com.ahuva.stock_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepository extends JpaRepository<Inventory, String> {
}
