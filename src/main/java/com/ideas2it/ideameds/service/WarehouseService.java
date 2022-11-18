package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse deleteWarehouse(Long warehouseId);
    Warehouse updateWarehouse(Warehouse warehouse);
    Warehouse getWarehouseById(Long warehouseId);
    List<Warehouse> getAllWarehouses();
    Warehouse addWarehouse(Warehouse warehouse);
}
