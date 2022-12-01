package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.WarehouseService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Controller for Warehouse details
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@RestController
public class WarehouseController {
    private final WarehouseService warehouseService;

    /**
     * <p>
     * Constructs a new object
     * </p>
     *
     * @param warehouseService creates a new instance for warehouse service
     */
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * <p>
     * Adds the warehouse
     * </p>
     *
     * @param warehouseDTO new warehouse to add
     * @return warehouse which was added
     */
    @PostMapping("/warehouse")
    public ResponseEntity<WarehouseDTO> addWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.addWarehouse(warehouseDTO));
    }

    /**
     * <p>
     * Gets all the warehouses
     * </p>
     *
     * @return list of all warehouses
     */
    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getAllWarehouses());
    }

    /**
     * <p>
     * gets warehouse by id
     * </p>
     *
     * @param warehouseId warehouse id to get warehouse
     * @return warehouse using id
     * @throws CustomException throws when the warehouse not found
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable("warehouseId") Long warehouseId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getWarehouseById(warehouseId));
    }

    /**
     * <p>
     * updates the warehouse
     * </p>
     *
     * @param warehouseDTO warehouse to update
     * @return updated warehouse
     * @throws CustomException throws when the warehouse is not found
     */
    @PutMapping("/warehouse")
    public WarehouseDTO updateWarehouse(@RequestBody WarehouseDTO warehouseDTO) throws CustomException {
        return warehouseService.updateWarehouse(warehouseDTO);
    }

    /**
     * <p>
     * deletes the warehouse
     * </p>
     *
     * @param warehouseId warehouse id to delete warehouse
     * @return response for deletion
     * @throws CustomException throws when the warehouse is not found
     */
    @PutMapping("/warehouse/delete/{warehouseId}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable("warehouseId") Long warehouseId) throws CustomException {
        Long warehouseById = warehouseService.deleteWarehouseByWarehouseId(warehouseId);
        if (warehouseById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(warehouseById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}
