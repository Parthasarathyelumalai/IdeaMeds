/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.WarehouseRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for Warehouse
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final DateTimeValidation dateTimeValidation;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, DateTimeValidation dateTimeValidation) {
        this.warehouseRepository = warehouseRepository;
        this.dateTimeValidation = dateTimeValidation;
    }

    /**
     *{@inheritDoc}
     */
    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        warehouse.setCreatedAt(dateTimeValidation.getDate());
        warehouse.setModifiedAt(dateTimeValidation.getDate());
        return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDTO.class);
    }

    /**
     *{@inheritDoc}
     */
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouse -> modelMapper
                        .map(warehouse, WarehouseDTO.class)).toList();
    }

    /**
     *{@inheritDoc}
     */
    public WarehouseDTO getWarehouseById(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse.isPresent()){
            return modelMapper.map(warehouse, WarehouseDTO.class);
        } else throw new CustomException(Constants.WAREHOUSE_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public WarehouseDTO updateWarehouse(WarehouseDTO warehouseDTO) throws CustomException {
        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        Optional<Warehouse> existWarehouse = warehouseRepository.findById(warehouseDTO.getWarehouseId());
        if(existWarehouse.isPresent()) {
            warehouse.setCreatedAt(existWarehouse.get().getCreatedAt());
            warehouse.setModifiedAt(dateTimeValidation.getDate());
            return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDTO.class);
        } else throw new CustomException(Constants.WAREHOUSE_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public Long deleteWarehouse(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse.isPresent()) {
            warehouse.get().setDeletedStatus(1);
            return warehouseRepository.save(warehouse.get()).getWarehouseId();
        } else throw new CustomException(Constants.WAREHOUSE_NOT_FOUND);
    }
}
