/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.BrandService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Brand details
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    /**
     * Constructs a new object for the corresponding services
     *
     * @param brandService creates new instance for the brand service
     */
    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * Adds the new brand record to the database.
     * Each entry contains validation to ensure the valid brand
     * multiple brand records cannot have the same name.
     * Brand contains entries about the medicine brand
     * A brand can be assigned to many brand Items
     *
     * @param brandDTO new brandDTO which will be converted to brand
     * @return brand which was added in the database successfully
     * @throws CustomException throws when the new brand name is already exist
     */
    @PostMapping
    public ResponseEntity<BrandDTO> addBrand(@Valid @RequestBody BrandDTO brandDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.addBrand(brandDTO));
    }

    /**
     * Gets all the brandDTO available in the database
     * which will be used for some corresponding scenarios
     * to show all the brand info.
     *
     * @return list of all brand if it's available in the database
     *         null if there is no record
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getAllBrands());
    }

    /**
     * gets brand item using brand name got from the request.
     * A brand name should be exact same compared with brand
     * name available in the database.
     *
     * @param brandName contains a brand name from the
     *                  request to get the brand record
     * @return brand after getting it using the valid brand name
     * @throws CustomException throws when the brand not fount using the
     *                         brand name from the request
     */
    @GetMapping("/by-name/{brandName}")
    public ResponseEntity<BrandDTO> getBrandByBrandName(@PathVariable String brandName) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getBrandByBrandName(brandName));
    }

    /**
     * Gets brand using the requested id
     * A Brand id should be exact same compared with brand id
     * from the database
     *
     * @param brandId used to search through and get a valid brand Item
     * @return brand after the id gets a valid brand available in the database
     * @throws CustomException throws when the brand not found using the id from the request
     */
    @GetMapping("/{brandId}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable("brandId") Long brandId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getBrandById(brandId));
    }

    /**
     * Updates the existing brand in the database
     * The brand will be found by the id of the brand and gets updated
     * Update process requires a valid brand, to ensure it, each entry
     * have validation.
     *
     * @param brandDTO contains an updated brand DTO to update an existing record
     * @return Brand Dto after it was updated successfully
     * @throws CustomException throws when the brand was not found, happens when the brandId
     *                         was changed before updating
     */
    @PutMapping
    public ResponseEntity<BrandDTO> updateBrand(@Valid @RequestBody BrandDTO brandDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.updateBrand(brandDTO));
    }

    /**
     * Soft deletes the brand using the corresponding id.
     * brand id from the request will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param brandId used to get the brand to update it as deleted
     * @return response that the brand has deleted successfully
     *         after the brand has flagged deleted
     * @throws CustomException throws when brand is not found using the
     *                         corresponding id from the request
     */
    @PutMapping("/delete/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable("brandId") Long brandId) throws CustomException {
        Long brandById = brandService.deleteBrand(brandId);
        if (brandById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandById + Constants.DELETED_SUCCESSFULLY);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
        }
    }

}

