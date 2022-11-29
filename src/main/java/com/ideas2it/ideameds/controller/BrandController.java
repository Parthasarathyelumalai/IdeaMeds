package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.BrandService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Brand details
 *
 * @author Dinesh Kumar R
 * @since 2022-11-18
 * @version 1.0
 */
@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * <p>
     *     Adds a brand
     * </p>
     * @param brandDTO
     *        new brandDTO to add
     * @return brand after added
     */
    @PostMapping("/brand")
    public ResponseEntity<BrandDTO> addBrand(@Valid @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.addBrand(brandDTO));
    }

    /**
     * <p>
     *     Gets all the brands
     * </p>
     * @return list of all brands
     */
    @GetMapping("/brand")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getAllBrands());
    }

    /**
     * <p>
     *     gets brand by brand name
     * </p>
     * @param brandName
     *        contains a name to get the brand
     * @return brand by using the brand name
     * @throws CustomException
     *         throws when brand is not found
     */
    @GetMapping("/getBrandByName/{brandName}")
    public ResponseEntity<BrandDTO> getBrandByBrandName(@PathVariable String brandName) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getBrandByBrandName(brandName));
    }

    /**
     *<p>
     *     Gets brand by id
     *</p>
     * @param brandId
     *        brand id to get brand
     * @return brand using the id
     * @throws CustomException
     *         throws when the brand was not found
     */
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable("brandId") Long brandId) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.getBrandById(brandId));
    }

    /**
     * <p>
     *     updates the brand
     * </p>
     * @param brandDTO
     *        brandDto to be updated
     * @return updated brandDto
     * @throws CustomException
     *         throws when the brand was not found
     */
    @PutMapping("/brand")
    public ResponseEntity<BrandDTO> updateBrand(@Valid @RequestBody BrandDTO brandDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.updateBrand(brandDTO));
    }

    /**
     * <p>
     *     Deletes the brand
     * </p>
     * @param brandId
     *        corresponding brand Id to delete
     * @return response for deletion
     * @throws CustomException
     *         throws when brand is not found
     */
    @PutMapping("/brand/delete/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable("brandId") Long brandId) throws CustomException {
        Long brandById = brandService.deleteBrand(brandId);
        if(brandById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }

}

