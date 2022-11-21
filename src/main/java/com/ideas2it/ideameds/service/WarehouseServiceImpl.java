package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    public Warehouse getWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).get();
    }
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        warehouse.setDeletedStatus(1);
        return warehouseRepository.save(warehouse);
    }
}
