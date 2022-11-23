package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
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
     * @param brand
     *        new brand to add
     * @return brand after added
     */
    @PostMapping("/brand")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    /**
     * <p>
     *     Gets all the brands
     * </p>
     * @return list of all brands
     */
    @GetMapping("/brand")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
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
    public Brand getBrandById(@PathVariable("brandId") Long brandId) {
        return brandService.getBrandById(brandId);
    }

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

