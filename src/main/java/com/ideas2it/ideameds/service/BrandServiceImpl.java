package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{

    private final BrandRepository brandRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandDTO addBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        return modelMapper.map(brandRepository.save(brand), BrandDTO.class);
    }
    public List<BrandDTO> getAllBrands() {
        List<BrandDTO> brandDTOs = new ArrayList<>();
        List<Brand> brands = brandRepository.findAll();
        for(Brand brand : brands) {
            brandDTOs.add(modelMapper.map(brand, BrandDTO.class));
        }
        return brandDTOs;
    }
    public BrandDTO getBrandByBrandName(String brandName) {
        return modelMapper.map(brandRepository.getBrandByBrandName(brandName), BrandDTO.class);
    }
    public BrandDTO getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId).get();
        return modelMapper.map(brand, BrandDTO.class);
    }
    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }
    public Brand deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId).get();
        brand.setDeletedStatus(1);
        return brandRepository.save(brand);
    }
}
