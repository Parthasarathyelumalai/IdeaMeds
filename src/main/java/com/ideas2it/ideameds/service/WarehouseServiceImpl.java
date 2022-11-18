package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl {
    private WarehouseRepository warehouseRepository;

    Warehouse deleteWarehouse(Long warehouseId) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        warehouse.setDeletedStatus(true);
        return warehouseRepository.save(warehouse);
    }

    Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    Warehouse getWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).get();
    }
    List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
}
