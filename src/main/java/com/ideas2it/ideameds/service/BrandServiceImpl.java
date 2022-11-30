/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.repository.BrandRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for Brand
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@Component
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     *{@inheritDoc}
     */
    public BrandDTO addBrand(BrandDTO brandDTO) {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        brand.setCreatedAt(DateTimeValidation.getDate());
        brand.setModifiedAt(DateTimeValidation.getDate());
        return modelMapper.map(brandRepository.save(brand), BrandDTO.class);
    }

    /**
     *{@inheritDoc}
     */
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> modelMapper
                        .map(brand, BrandDTO.class)).toList();
    }

    /**
     *{@inheritDoc}
     */
    public BrandDTO getBrandByBrandName(String brandName) throws CustomException {
        Brand brand = brandRepository.getBrandByBrandName(brandName);
        if (brand != null) {
            return modelMapper.map(brand, BrandDTO.class);
        } else throw new CustomException(Constants.BRAND_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public BrandDTO getBrandById(Long brandId) throws CustomException {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if (brand.isPresent()) {
            return modelMapper.map(brand.get(), BrandDTO.class);
        } else throw new CustomException(Constants.BRAND_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public BrandDTO updateBrand(BrandDTO brandDTO) throws CustomException {
        Brand brand = modelMapper.map(brandDTO, Brand.class);
        Optional<Brand> existBrand = brandRepository.findById(brandDTO.getBrandId());
        if (existBrand.isPresent()) {
            brand.setCreatedAt(existBrand.get().getCreatedAt());
            brand.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(brandRepository.save(brand), BrandDTO.class);
        } else throw new CustomException(Constants.BRAND_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public Long deleteBrand(Long brandId) throws CustomException {
        Optional<Brand> brand = brandRepository.findById(brandId);
        if(brand.isPresent()) {
            brand.get().setDeletedStatus(true);
            return brandRepository.save(brand.get()).getBrandId();
        } else throw new CustomException(Constants.BRAND_NOT_FOUND);
    }
}
