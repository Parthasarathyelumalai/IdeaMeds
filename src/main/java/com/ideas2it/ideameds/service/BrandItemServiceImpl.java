/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItem;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.BrandItemRepository;
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
public class BrandItemServiceImpl implements BrandItemService {

    private final BrandItemRepository brandItemRepository;
    private final WarehouseRepository warehouseRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates instance for the classes
     *
     * @param brandItemRepository create object for brand items repository
     * @param warehouseRepository  create object for warehouse repository
     */
    @Autowired
    public BrandItemServiceImpl(BrandItemRepository brandItemRepository, WarehouseRepository warehouseRepository) {
        this.brandItemRepository = brandItemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemDTO addBrandItem(BrandItemDTO brandItemDTO, MedicineDTO medicineDTO, BrandDTO brandDTO) throws CustomException {

        if (brandItemRepository
                .findBrandItemByBrandItemName(brandItemDTO.getBrandItemName()).isEmpty()) {
            BrandItem brandItem = modelMapper.map(brandItemDTO, BrandItem.class);
            brandItem.setMedicine(modelMapper.map(medicineDTO, Medicine.class));
            brandItem.setBrand(modelMapper.map(brandDTO, Brand.class));
            brandItem.setCreatedAt(DateTimeValidation.getDate());
            brandItem.setModifiedAt(DateTimeValidation.getDate());
            BrandItem newBrandItem = brandItemRepository.save(brandItem);
            return setToBrandItem(newBrandItem, brandItem.getBrand(), brandItem.getMedicine());
        } else {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, Constants.BRAND_ITEM_NAME_EXIST);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandItemDTO> getAllBrandItems() {
        List<BrandItem> brandItems = brandItemRepository.findAll();
        List<BrandItemDTO> brandItemsDTOs = new ArrayList<>();

        for (BrandItem brandItem: brandItems) {
            brandItemsDTOs.add(setToBrandItem(brandItem,
                                              brandItem.getBrand(),
                                              brandItem.getMedicine()));
        }
        return brandItemsDTOs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemDTO getBrandItemById(Long brandItemId) throws CustomException {
        Optional<BrandItem> brandItem = brandItemRepository.findById(brandItemId);

        if (brandItem.isPresent()) {
            BrandItemDTO brandItemDTO = modelMapper.map(brandItem.get(), BrandItemDTO.class);
            brandItemDTO.setBrandDTO(modelMapper.map(brandItem.get().getBrand(), BrandDTO.class));
            brandItemDTO.setMedicineDTO(modelMapper.map(brandItem.get().getMedicine(), MedicineDTO.class));
            return brandItemDTO;
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandDTO getBrandByBrandItemId(Long brandItemId) throws CustomException {
        Optional<BrandItem> brandItem = brandItemRepository.findById(brandItemId);

        if (brandItem.isPresent()) {
            Brand brand = brandItem.get().getBrand();

            if (brand != null) {
                return modelMapper.map(brand, BrandDTO.class);
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MedicineDTO getMedicineByBrandItemId(Long brandItemId) throws CustomException {
        Optional<BrandItem> brandItem = brandItemRepository.findById(brandItemId);

        if (brandItem.isPresent()) {
            Medicine medicine = brandItem.get().getMedicine();
            if (medicine != null) {
                return modelMapper.map(medicine, MedicineDTO.class);
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_NOT_FOUND);
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemDTO updateBrandItem(BrandItemDTO brandItemDTO) throws CustomException {
        BrandItem brandItem = modelMapper.map(brandItemDTO, BrandItem.class);
        Optional<BrandItem> existingBrandItem = brandItemRepository.findById(brandItemDTO.getBrandItemId());

        if (existingBrandItem.isPresent()) {
            brandItem.setCreatedAt(existingBrandItem.get().getCreatedAt());
            brandItem.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(brandItemRepository.save(brandItem), BrandItemDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long deleteBrandItem(Long brandItemId) throws CustomException {
        Optional<BrandItem> brandItem = brandItemRepository.findById(brandItemId);

        if (brandItem.isPresent()) {
            brandItem.get().setDeleted(true);
            brandItem.get().setModifiedAt(DateTimeValidation.getDate());
            return brandItemRepository.save(brandItem.get()).getBrandItemId();
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrandItemDTO assignToWarehouse(WarehouseDTO warehouseDTO, Long brandItemId) throws CustomException {
        List<Warehouse> warehouses = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseDTO.getWarehouseId());

        if (warehouse.isPresent()) {
            warehouse.get().setModifiedAt(DateTimeValidation.getDate());
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
        }
        warehouses.add(warehouse.get());
        Optional<BrandItem> brandItem = brandItemRepository.findById(brandItemId);
        if (brandItem.isPresent()) {
            brandItem.get().setWarehouses(warehouses);
            return modelMapper.map(brandItemRepository.save(brandItem.get()), BrandItemDTO.class);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BrandItemDTO> getBrandItemBySearch(String medicineName) throws CustomException {
        Optional<List<BrandItem>> existingBrandItems = brandItemRepository.findAllByBrandItemNameContainingIgnoreCase(medicineName);

        if (existingBrandItems.isPresent()) {
            return existingBrandItems.get().stream()
                    .map(brandItems -> modelMapper.map(brandItems, BrandItemDTO.class))
                    .toList();
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
        }
    }

    /**
     * {@inheritDoc}
     */
    public BrandItemDTO getBrandItemByName(String brandItemName) {
        Optional<BrandItem> existingBrandItem = brandItemRepository.findBrandItemByBrandItemName(brandItemName);
        return existingBrandItem.map(brandItem -> setToBrandItem(brandItem,
                brandItem.getBrand(),
                brandItem.getMedicine())).orElse(null);
    }


    /**
     * <p>
     * sets brand and medicine for the corresponding brand item
     * and converts to brand item DTO
     * </p>
     * @param brandItem
     *        brand item to assign brand and medicine
     * @param brand
     *        to assign a brand to a brand item
     * @param medicine
     *        to assign a medicine to a brand item
     * @return brand item dto which contains brand item information
     *  and also brand and medicine
     */
    private BrandItemDTO setToBrandItem(BrandItem brandItem, Brand brand, Medicine medicine) {
        BrandItemDTO brandItemDTO = modelMapper.map(brandItem, BrandItemDTO.class);
        brandItemDTO.setBrandDTO(modelMapper.map(brand, BrandDTO.class));
        brandItemDTO.setMedicineDTO(modelMapper.map(medicine, MedicineDTO.class));
        return brandItemDTO;
    }
}
