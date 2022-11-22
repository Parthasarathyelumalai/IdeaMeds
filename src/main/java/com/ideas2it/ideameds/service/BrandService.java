package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;

import java.util.List;

public interface BrandService {
    public Brand addBrand(Brand brand);
    public List<Brand> getAllBrands();
    public Brand getBrandById(Long brandId);
    public Brand getBrandByBrandName(String brandName);
    public Brand updateBrand(Brand brand);
    public Brand deleteBrand(Long brandId);
}
