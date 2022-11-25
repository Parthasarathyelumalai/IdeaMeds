/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for Warehouse
 * </p>
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
public interface WarehouseService {
    /**
     * <p>
     *     adds a warehouse
     * </p>
     * @param warehouseDTO
     *        to add a new warehouse
     * @return added warehouse
     */
    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO);

    /**
     * <p>
     *     gets all the warehouse
     * </p>
     * @return list of warehouses
     */
    public List<WarehouseDTO> getAllWarehouses();

    /**
     * <p>
     *     gets warehouse by id
     * </p>
     * @param warehouseId
     *        to get the warehouse
     * @return warehouse using the id
     * @throws CustomException
     *         throws when the warehouse is not found
     */
    public WarehouseDTO getWarehouseById(Long warehouseId) throws CustomException;

    /**
     * <p>
     *     updates the warehouse
     * </p>
     * @param warehouseDTO
     *        warehouse to be updated
     * @return updated warehouse
     * @throws CustomException
     *         throws when the warehouse is not found
     */
    public WarehouseDTO updateWarehouse(WarehouseDTO warehouseDTO) throws CustomException;

    /**
     * <p>
     *     Deletes a warehouse
     * </p>
     * @param warehouseId
     *        to get the warehouse
     * @return warehouse id of deleted warehouse
     * @throws CustomException
     *         throws when the warehouse is not found
     */
    public Long deleteWarehouse(Long warehouseId) throws CustomException;

}
