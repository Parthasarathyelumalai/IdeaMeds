/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.BrandItemService;
import com.ideas2it.ideameds.service.BrandService;
import com.ideas2it.ideameds.service.MedicineService;
import com.ideas2it.ideameds.service.WarehouseService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Brand Items
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-22
 */
@RestController
public class BrandItemsController {

    private final BrandItemService brandItemService;
    private final WarehouseService warehouseService;
    private final BrandService brandService;
    private final MedicineService medicineService;

    /**
     * Constructs a new object for the corresponding services
     *
     * @param brandItemService creates new instance for brand Items Service
     * @param warehouseService  creates new instance for warehouse service
     * @param brandService      creates new instance for brand service
     * @param medicineService   creates new instance for medicine service
     */
    @Autowired
    public BrandItemsController(BrandItemService brandItemService,
                                WarehouseService warehouseService,
                                BrandService brandService,
                                MedicineService medicineService) {
        this.brandItemService = brandItemService;
        this.warehouseService = warehouseService;
        this.brandService = brandService;
        this.medicineService = medicineService;
    }

    /**
     * Adds the new brand Item record to the database.
     * Each entry contains validation to ensure the valid brand item
     * multiple brand items record cannot have the same name.
     * Brand Items contains connection between medicine and brand
     * A brand Item has association with medicine and brand
     *
     * @param brandItemDTO new brand item DTO which will be converted to brand item
     * @param medicineId    to get medicine and will be assigned to the corresponding brand Item
     * @param brandId       to get brand and will be assigned to the corresponding brand Item
     * @return brand Item Dto after the brand item is created in the database successfully
     * @throws CustomException throws when the brand using the requested brand id not found and
     *                         throws when the medicine using the medicine id not found and
     *                         throws when the new brand item name already exist
     */
    @PostMapping("/brand-item/{medicineId}/{brandId}")
    public ResponseEntity<BrandItemDTO> addBrandItem(@Valid @RequestBody BrandItemDTO brandItemDTO,
                                                     @PathVariable("medicineId") Long medicineId,
                                                     @PathVariable("brandId") Long brandId) throws CustomException {
        MedicineDTO medicineDTO = medicineService.getMedicineById(medicineId);
        BrandDTO brandDTO = brandService.getBrandById(brandId);
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService
                .addBrandItem(brandItemDTO, medicineDTO, brandDTO));
    }

    /**
     * Gets all the brand Items DTOs available in the database
     * which will be used for corresponding associated with
     * brand items
     *
     * @return list of all brand if it's available in the database
     *         null if there is no record
     */
    @GetMapping("/brand-item/get-all")
    public ResponseEntity<List<BrandItemDTO>> getAllBrandItems() {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService.getAllBrandItems());
    }

    /**
     * This function is used to get a brand item by its id
     * A Brand id should be exact same compared with brand id
     * from the database
     *
     * @param brandItemId The id of the brand item you want to get.
     * @return brand item by id has found successfully
     * @throws CustomException throws when the brand item not found
     *                         using the brand items id from the request
     */
    @GetMapping("/brand-item/{brandItemId}")
    public ResponseEntity<BrandItemDTO> getBrandItemById(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService.getBrandItemById(brandItemId));
    }

    /**
     * Gets a brand item by name
     * A brand Item name should be exact same compared with brand
     * name available in the database.
     *
     * @param brandItemName The name of the brand item to get.
     * @return A brand item by name when the name matches a
     *         brand item name from the database
     * @throws CustomException throws when brand item not found
     *                         using the brand Item name from the request
     */
    @GetMapping("/brand-item/by-name/{brandItemName}")
    public ResponseEntity<BrandItemDTO> getBrandItemByName(@PathVariable("brandItemName") String brandItemName) throws CustomException {
        BrandItemDTO brandItemDTO = brandItemService.getBrandItemByName(brandItemName);
        if (brandItemDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandItemDTO);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.BRAND_ITEM_NOT_FOUND);
    }

    /**
     * Gets list of brand items related with medicine name
     * or a partial word from the request
     * Searches the phrase with brand items name using like query.
     * A brand Item name can be similar or exact compared with the
     * name available in the database.
     *
     * @param medicineName medicine name or a word to get brand items
     * @return list of brand items using user input
     * @throws CustomException throws when brand item not found
     */
    @GetMapping("/brand-items/by-search/{medicineName}")
    public ResponseEntity<List<BrandItemDTO>> getBrandItemBySearch(@PathVariable String medicineName) throws CustomException {
        List<BrandItemDTO> brandItemDTOs = brandItemService.getBrandItemBySearch(medicineName);
        return ResponseEntity.status(HttpStatus.OK).body(brandItemDTOs);
    }

    /**
     * This function is used to get the brand of a brand item by its id
     * A Brand Item id should be exact same compared with brand Item id
     * from the database
     *
     * @param brandItemId Id to get brand Items
     * @return brand from brand Items after getting the brand items
     * @throws CustomException throws when brand Item using the id not found and
     *                         throws when brand from the brand item not found
     */
    @GetMapping("/brand-item/get-brand/{brandItemId}")
    public ResponseEntity<BrandDTO> getBrandByBrandItemId(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService.getBrandByBrandItemId(brandItemId));
    }

    /**
     * This function is used to get the medicine details by brand item id
     * A Brand Item id should be exact same compared with brand id
     * from the database
     *
     * @param brandItemId Id to get brand Items
     * @return medicine from brand Items after getting the brand items
     * @throws CustomException throws when brand Item using the id not found and
     *                         throws when medicine from the brand item not found
     */
    @GetMapping("/brand-item/get-medicine/{brandItemId}")
    public ResponseEntity<MedicineDTO> getMedicineByBrandItemId(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService.getMedicineByBrandItemId(brandItemId));
    }

    /**
     * Updates the existing brand Items in the database
     * The brand items will be found by the id and gets updated
     * Update process requires a valid brand items, to ensure it, each entry
     * have validation.
     * Changing a brand items ID to invalid format throws exception
     *
     * @param brandItemDTO The Updated Brand Item to be updated
     * @return Brand Items Dto after it was updated successfully
     * @throws CustomException throws when the brand was not found, happens when the brand Items ID
     *                         was changed before updating
     */
    @PutMapping("/brand-items")
    public ResponseEntity<BrandItemDTO> updateBrandItem(@Valid @RequestBody BrandItemDTO brandItemDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemService.updateBrandItem(brandItemDTO));
    }

    /**
     * Assigns brand Item to the corresponding warehouse,
     * in which warehouse holds the collection of brand item and
     * brand items hold collections of warehouse available information.
     * Brand Item record can be assigned to a corresponding
     * warehouse one at a time.
     *
     * @param warehouseId contains warehouse id to get warehouse
     *                    and warehouse used to assign the brand item
     * @param brandItemId to get brand item by id and assign it to warehouse
     * @return Brand Items After successfully Assigning
     * @throws CustomException throws when warehouse not found and also
     *                         throws when brand item not found
     */
    @PutMapping("/brand-item/assign/{brandItemId}/{warehouseId}")
    public ResponseEntity<BrandItemDTO> assignToWarehouse(@PathVariable("warehouseId") Long warehouseId,
                                                           @PathVariable("brandItemId") Long brandItemId) throws CustomException {
        WarehouseDTO warehouseDTO = warehouseService.getWarehouseById(warehouseId);
        if (warehouseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(brandItemService.assignToWarehouse(warehouseDTO, brandItemId));
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
    }

    /**
     * Soft deletes the brand Items using the corresponding id.
     * brand id from the request will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param brandItemId id to delete the corresponding brand Item
     * @return response that the brand Item has deleted successfully
     *         after the brand items record has flagged deleted
     * @throws CustomException throws when brand item not found using the
     *                         corresponding brand items id
     */
    @PutMapping("/brand-item/delete/{brandItemId}")
    public ResponseEntity<String> deleteBrandItem(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        Long brandItemById = brandItemService.deleteBrandItem(brandItemId);
        if (brandItemById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandItemById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}