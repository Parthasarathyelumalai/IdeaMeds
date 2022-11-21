package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * <p>
     *     Adds the warehouse
     * </p>
     * @param warehouse
     *        new warehouse to add
     * @return warehouse which was added
     */
    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    /**
     * <p>
     *     Gets all the warehouses
     * </p>
     * @return list of all warehouses
     */
    @GetMapping("/warehouse")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    /**
     * <p>
     *     gets warehouse by id
     * </p>
     * @param warehouseId
     *        warehouse id to get warehouse
     * @return warehouse using id
     */
    @GetMapping("/warehouse/{warehouseId}")
    public Warehouse getWarehouseById(@PathVariable("warehouseId") Long warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }

    /**
     * <p>
     *     updates the warehouse
     * </p>
     * @param warehouse
     *        warehouse to update
     * @return updated warehouse
     */
    @PutMapping("/warehouse")
    public Warehouse updateWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.updateWarehouse(warehouse);
    }

    /**
     * <p>
     *     deletes the warehouse
     * </p>
     * @param warehouseId
     *        warehouse id to delete warehouse
     * @return response for deletion
     */
    @PutMapping("/warehouse/delete/{warehouseId}")
    public Warehouse deleteWarehouse(@PathVariable("warehouseId") Long warehouseId) {
        return warehouseService.deleteWarehouse(warehouseId);
    }
}
