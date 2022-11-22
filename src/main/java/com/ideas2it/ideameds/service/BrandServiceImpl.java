package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
    public Brand getBrandByBrandName(String brandName) {
        return brandRepository.getBrandByBrandName(brandName);
    }
    public Brand getBrandById(Long brandId) {
        return brandRepository.findById(brandId).get();
    }
    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    public Brand deleteBrand(Long brandId) {
        Brand brand = getBrandById(brandId);
        brand.setDeletedStatus(1);
        return brandRepository.save(brand);
    }
}
