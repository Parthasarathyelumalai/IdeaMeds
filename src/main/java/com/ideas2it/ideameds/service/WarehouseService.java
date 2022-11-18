package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Warehouse;

import java.util.List;

public interface WarehouseService {
    public Warehouse deleteWarehouse(Long warehouseId);
    public Warehouse updateWarehouse(Warehouse warehouse);
    public Warehouse getWarehouseById(Long warehouseId);
    public List<Warehouse> getAllWarehouses();
    public Warehouse addWarehouse(Warehouse warehouse);
}
