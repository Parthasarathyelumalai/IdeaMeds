package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Brand details
 *
 * @author - Dinesh Kumar R
 * @since - 18/11/2022
 * @version - 1.0
 */
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/brand")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }
    @GetMapping("/brand")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }
    @GetMapping("/brand/{brandId}")
    public Brand getBrandById(@PathVariable("brandId") Long medicineId) {
        return brandService.getBrandById(medicineId);
    }
    @GetMapping("/brand/getMedicine/{medicineBrand}")
    public List<Medicine> getMedicineByBrand(@PathVariable("medicineBrand") String medicineBrand) {
        return brandService.getMedicineByBrand(medicineBrand);
    }

    @PutMapping("/brand")
    public Brand updateBrand(@RequestBody Brand brand) {
        return brandService.updateBrand(brand);
    }
    @PutMapping("/brand/delete/{brandId}")
    public Brand deleteBrand(@PathVariable("brandId") Long brandId) {
        return brandService.deleteBrand(brandId);
    }
}