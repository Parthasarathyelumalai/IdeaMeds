package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.dto.WarehouseResponseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.WarehouseService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Controller for Warehouse details
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
     * Constructs a new object for the corresponding services
     * </p>
     *
     * @param warehouseService creates a new instance for warehouse service
     */
    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /**
     * Adds the new warehouse record to the database.
     * Each entry contains validation to ensure the valid warehouse.
     * Multiple warehouse records cannot have the same name.
     * Warehouse contains the information about the warehouse.
     * Warehouse association contains the medicine availability info
     *
     * @param warehouseDTO new warehouseDTO which will be converted to warehouse
     * @return warehouse which was added successfully in the database
     * @throws CustomException throws when the new warehouse name is already exist
     */
    @PostMapping("/warehouse")
    public ResponseEntity<WarehouseDTO> addWarehouse(@RequestBody WarehouseDTO warehouseDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.addWarehouse(warehouseDTO));
    }

    /**
     * Gets all the WarehouseDTO available in the database
     *
     * @return list of all warehouses if it's available in the database
     *         null if there is no record
     */
    @GetMapping("/warehouse")
    public ResponseEntity<List<WarehouseDTO>> getAllWarehouses() {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getAllWarehouses());
    }

    /**
     * Gets warehouse using the requested id
     * A Warehouse id should be exact same compared with brand id
     * from the database
     *
     * @param warehouseId warehouse id to get warehouse
     * @return warehouse after the id gets a valid warehouse available in the database
     * @throws CustomException throws when the warehouse not found using the id from the request
     */
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable("warehouseId") Long warehouseId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(warehouseService.getWarehouseById(warehouseId));
    }

    /**
     * Gets warehouse details and available stocks in the warehouse
     *
     * @param warehouseId warehouse id to get warehouse
     * @return warehouse using id and brand items available in the warehouse
     * @throws CustomException throws when the warehouse not found
     */
    @GetMapping("/warehouse-and-stocks/{warehouseId}")
    public ResponseEntity<WarehouseResponseDTO> getWarehouseAndStocksById(@PathVariable("warehouseId") Long warehouseId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(warehouseService.getWarehouseAndStocksById(warehouseId));
    }

    /**
     * Updates the existing warehouse in the database
     * The warehouse will be found by the id and gets updated
     * Update process requires a valid warehouse, to ensure it, each entry
     * have validation.
     *
     * @param warehouseDTO warehouse to update
     * @return warehouse Dto after it was updated successfully
     * @throws CustomException throws when the warehouse is not found
     */
    @PutMapping("/warehouse")
    public WarehouseDTO updateWarehouse(@RequestBody WarehouseDTO warehouseDTO) throws CustomException {
        return warehouseService.updateWarehouse(warehouseDTO);
    }

    /**
     * Soft deletes the warehouse using the corresponding id.
     * warehouse id from the request will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param warehouseId used to get the warehouse to update it as deleted
     * @return response for deletion
     * @throws CustomException throws when the warehouse is not found
     */
    @PutMapping("/warehouse/delete/{warehouseId}")
    public ResponseEntity<String> deleteWarehouseById(@PathVariable("warehouseId") Long warehouseId) throws CustomException {
        Long warehouseById = warehouseService.deleteWarehouseById(warehouseId);
        if (warehouseById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(warehouseById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}
