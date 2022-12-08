/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.dto.WarehouseResponseDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for Warehouse
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
public interface WarehouseService {
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
    WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) throws CustomException;

    /**
     * Gets all the WarehouseDTO available in the database
     *
     * @return list of all warehouses if it's available in the database
     *         null if there is no record
     */
    List<WarehouseDTO> getAllWarehouses();

    /**
     * Gets warehouse using the requested id
     * A Warehouse id should be exact same compared with brand id
     * from the database
     *
     * @param warehouseId warehouse id to get warehouse
     * @return warehouse after the id gets a valid warehouse available in the database
     * @throws CustomException throws when the warehouse not found using the id from the request
     */
    WarehouseDTO getWarehouseById(Long warehouseId) throws CustomException;

    /**
     * Gets warehouse details and available stocks in the warehouse
     * A Warehouse id should be exact same compared with brand id
     * from the database
     *
     * @param warehouseId warehouse id to get warehouse
     * @return warehouse using id and brand items available in the warehouse
     * @throws CustomException throws when the warehouse not found
     */
    WarehouseResponseDTO getWarehouseAndStocksById(Long warehouseId) throws CustomException;

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
    WarehouseDTO updateWarehouse(WarehouseDTO warehouseDTO) throws CustomException;

    /**
     * Soft deletes the warehouse using the corresponding id.
     * warehouse id from the request will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param warehouseId used to get the warehouse to update it as deleted
     * @return response for deletion
     * @throws CustomException throws when the warehouse is not found
     */
    Long deleteWarehouseById(Long warehouseId) throws CustomException;

}