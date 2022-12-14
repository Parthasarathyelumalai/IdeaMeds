/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.dto.WarehouseResponseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.WarehouseRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /**
     * Create instance for the class
     * @param warehouseRepository create instance for warehouse repository
     */
    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) throws CustomException {

        if (warehouseRepository
                .findByWarehouseName(warehouseDTO.getWarehouseName()).isEmpty()) {
            Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
            warehouse.setCreatedAt(DateTimeValidation.getDate());
            warehouse.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.WAREHOUSE_NAME_EXIST);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(warehouse -> modelMapper
                        .map(warehouse, WarehouseDTO.class)).toList();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public WarehouseDTO getWarehouseDTOById(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);

        if (warehouse.isPresent()) {
            return modelMapper.map(warehouse, WarehouseDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
    }

    @Override
    public Warehouse getWarehouseById(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);

        if (warehouse.isPresent()) {
            return warehouse.get();
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public WarehouseResponseDTO getWarehouseAndStocksById(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);

        if (warehouse.isPresent()){
            WarehouseResponseDTO warehouseResponseDTO = modelMapper.map(warehouse, WarehouseResponseDTO.class);
            warehouseResponseDTO.setBrandItemsDTOs(warehouse.get().getBrandItems()
                    .stream().map(brandItems -> modelMapper.map(brandItems, BrandItemDTO.class)).toList());
            return warehouseResponseDTO;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public WarehouseDTO updateWarehouse(WarehouseDTO warehouseDTO) throws CustomException {
        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        Optional<Warehouse> existingWarehouse = warehouseRepository.findById(warehouseDTO.getWarehouseId());

        if(existingWarehouse.isPresent()) {
            warehouse.setCreatedAt(existingWarehouse.get().getCreatedAt());
            warehouse.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Long deleteWarehouseById(Long warehouseId) throws CustomException {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);

        if (warehouse.isPresent()) {
            warehouse.get().setDeleted(true);
            return warehouseRepository.save(warehouse.get()).getWarehouseId();
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
    }
}