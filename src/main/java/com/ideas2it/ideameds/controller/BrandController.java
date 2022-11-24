package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Brand details
 *
 * @author Dinesh Kumar R
 * @since 18/11/2022
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
    public BrandDTO addBrand(@RequestBody BrandDTO brandDTO) {
        return brandService.addBrand(brandDTO);
    }

    /**
     * <p>
     *     Gets all the brands
     * </p>
     * @return list of all brands
     */
    @GetMapping("/brand")
    public List<BrandDTO> getAllBrands() {
        return brandService.getAllBrands();
    }

    /**
     * <p>
     *     gets brand by brand name
     * </p>
     * @param brandName
     *        contains a name to get the brand
     * @return brand by using the brand name
     */
    @GetMapping("/getBrandByName/{brandName}")
    public BrandDTO getBrandByBrandName(@PathVariable String brandName) {
        return brandService.getBrandByBrandName(brandName);
    }

    /**
     *<p>
     *     Gets brand by id
     *</p>
     * @param brandId
     *        brand id to get brand
     * @return brand using the id
     */
    @GetMapping("/brand/{brandId}")
    public BrandDTO getBrandById(@PathVariable("brandId") Long brandId) {
        return brandService.getBrandById(brandId);
    }

    /**
     * <p>
     *     updates the brand
     * </p>
     * @param brand
     *        brandDto to be updated
     * @return updated brandDto
     */
    @PutMapping("/brand")
    public Brand updateBrand(@RequestBody Brand brand) {
        return brandService.updateBrand(brand);
    }

    /**
     * <p>
     *     Deletes the brand
     * </p>
     * @param brandId
     *        corresponding brand Id to delete
     * @return response for deletion
     */
    @PutMapping("/brand/delete/{brandId}")
    public Brand deleteBrand(@PathVariable("brandId") Long brandId) {
        return brandService.deleteBrand(brandId);
    }

}

