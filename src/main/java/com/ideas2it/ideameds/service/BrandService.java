package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;

import java.util.List;

public interface BrandService {
    public BrandDTO addBrand(BrandDTO brandDTO);
    public List<BrandDTO> getAllBrands();
    public BrandDTO getBrandById(Long brandId);
    public BrandDTO getBrandByBrandName(String brandName);
    public Brand updateBrand(Brand brand);
    public Brand deleteBrand(Long brandId);
}
