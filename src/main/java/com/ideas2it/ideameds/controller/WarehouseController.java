package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable("warehouseId") Long warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }

    @PutMapping("/warehouse")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.updateWarehouse(warehouse);
    }

    @PutMapping("/warehouse/{warehouseId}")
    public Warehouse deleteWarehouse(@PathVariable("warehouseId") Long warehouseId) {
        return warehouseService.deleteWarehouse(warehouseId);
    }
}
