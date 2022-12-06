/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import com.ideas2it.ideameds.repository.WarehouseRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for Brand Items
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-21
 */
@Service
public class BrandItemsServiceImpl implements BrandItemsService {

    private final BrandItemsRepository brandItemsRepository;

    private final WarehouseRepository warehouseRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates instance for the classes
     *
     * @param brandItemsRepository create object for brand items repository
     * @param warehouseRepository  create object for warehouse repository
     */
    @Autowired
    public BrandItemsServiceImpl(BrandItemsRepository brandItemsRepository, WarehouseRepository warehouseRepository) {
        this.brandItemsRepository = brandItemsRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemsDTO addBrandItem(BrandItemsDTO brandItemsDTO, MedicineDTO medicineDTO, BrandDTO brandDTO) {
        BrandItems brandItems = modelMapper.map(brandItemsDTO, BrandItems.class);
        brandItems.setMedicine(modelMapper.map(medicineDTO, Medicine.class));
        brandItems.setBrand(modelMapper.map(brandDTO, Brand.class));
        brandItems.setCreatedAt(DateTimeValidation.getDate());
        brandItems.setModifiedAt(DateTimeValidation.getDate());
        BrandItems newBrandItems = brandItemsRepository.save(brandItems);
        return setBrandAndMedicineDTO(newBrandItems, brandItems.getBrand(), brandItems.getMedicine());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandItemsDTO> getAllBrandItems() {
        List<BrandItems> brandItemsList = brandItemsRepository.findAll();
        List<BrandItemsDTO> brandItemsDTOList = new ArrayList<>();
        for (BrandItems brandItems: brandItemsList) {
            brandItemsDTOList.add(setBrandAndMedicineDTO(brandItems,
                                                         brandItems.getBrand(),
                                                         brandItems.getMedicine()));
        }
        return brandItemsDTOList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemsDTO getBrandItemById(Long brandItemId) throws CustomException {
        Optional<BrandItems> brandItems = brandItemsRepository.findById(brandItemId);
        if (brandItems.isPresent()) {
            BrandItemsDTO brandItemsDTO = modelMapper.map(brandItems.get(), BrandItemsDTO.class);
            brandItemsDTO.setBrandDTO(modelMapper.map(brandItems.get().getBrand(), BrandDTO.class));
            brandItemsDTO.setMedicineDTO(modelMapper.map(brandItems.get().getMedicine(), MedicineDTO.class));
            return brandItemsDTO;
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandDTO getBrandByBrandItemId(Long brandItemId) throws CustomException {
        Optional<BrandItems> brandItems = brandItemsRepository.findById(brandItemId);
        if (brandItems.isPresent()) {
            Brand brand = brandItems.get().getBrand();
            if (brand != null) {
                return modelMapper.map(brand, BrandDTO.class);
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MedicineDTO getMedicineByBrandItemId(Long brandItemId) throws CustomException {
        Optional<BrandItems> brandItems = brandItemsRepository.findById(brandItemId);
        if (brandItems.isPresent()) {
            Medicine medicine = brandItems.get().getMedicine();
            if (medicine != null) {
                return modelMapper.map(medicine, MedicineDTO.class);
            } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemsDTO updateBrandItem(BrandItemsDTO brandItemsDTO) throws CustomException {
        BrandItems brandItems = modelMapper.map(brandItemsDTO, BrandItems.class);
        Optional<BrandItems> existBrandItems = brandItemsRepository.findById(brandItemsDTO.getBrandItemsId());
        if (existBrandItems.isPresent()) {
            brandItems.setCreatedAt(existBrandItems.get().getCreatedAt());
            brandItems.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(brandItemsRepository.save(brandItems), BrandItemsDTO.class);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long deleteBrandItem(Long brandItemId) throws CustomException {
        Optional<BrandItems> brandItem = brandItemsRepository.findById(brandItemId);
        if (brandItem.isPresent()) {
            brandItem.get().setDeletedStatus(true);
            brandItem.get().setModifiedAt(DateTimeValidation.getDate());
            return brandItemsRepository.save(brandItem.get()).getBrandItemsId();
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemsDTO assignToWarehouse(WarehouseDTO warehouseDTO, Long brandItemId) throws CustomException {
        List<Warehouse> warehouses = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseDTO.getWarehouseId());
        if (warehouse.isPresent()) {
            warehouse.get().setModifiedAt(DateTimeValidation.getDate());
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        warehouses.add(warehouse.get());
        Optional<BrandItems> brandItems = brandItemsRepository.findById(brandItemId);
        if (brandItems.isPresent()) {
            brandItems.get().setWarehouses(warehouses);
            return modelMapper.map(brandItemsRepository.save(brandItems.get()), BrandItemsDTO.class);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandItemsDTO> getByMedicineName(String medicineName) throws CustomException {
        Optional<List<BrandItems>> brandItemsList = brandItemsRepository.findAllByBrandItemNameContainingIgnoreCase(medicineName);
        if (brandItemsList.isPresent()) {
            return brandItemsList.get().stream()
                    .map(brandItems -> modelMapper.map(brandItems, BrandItemsDTO.class))
                    .toList();
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    public BrandItemsDTO getBrandItemByName(String brandItemName) throws CustomException {
        Optional<BrandItems> brandItems = brandItemsRepository.findBrandItemsByBrandItemName(brandItemName);
        if (brandItems.isPresent()) {
            return setBrandAndMedicineDTO(brandItems.get(),
                                          brandItems.get().getBrand(),
                                          brandItems.get().getMedicine());
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemsDTO getBrandItemByBrandItemName(String brandItemName) {
        return modelMapper.map(brandItemsRepository.findBrandItemsByBrandItemName(brandItemName), BrandItemsDTO.class);
    }

    /**
     * <p>
     * sets brand and medicine for the corresponding brand item
     * and converts to brand item DTO
     * </p>
     * @param brandItems
     *        brand item to assign brand and medicine
     * @param brand
     *        to assign a brand to a brand item
     * @param medicine
     *        to assign a medicine to a brand item
     * @return brand item dto which contains brand item information
     *  and also brand and medicine
     */
    private BrandItemsDTO setBrandAndMedicineDTO(BrandItems brandItems, Brand brand, Medicine medicine) {
        BrandItemsDTO brandItemsDTO = modelMapper.map(brandItems, BrandItemsDTO.class);
        brandItemsDTO.setBrandDTO(modelMapper.map(brand, BrandDTO.class));
        brandItemsDTO.setMedicineDTO(modelMapper.map(medicine, MedicineDTO.class));
        return brandItemsDTO;
    }
}
