/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the Brand
 * </p>
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
public interface BrandService {

    /**
     * <p>
     *     adds a new brand
     * </p>
     * @param brandDTO
     *        to add a new brand
     * @return added brand
     */
    public BrandDTO addBrand(BrandDTO brandDTO);

    /**
     * <p>
     *     Gets all brands
     * </p>
     * @return list of all brands
     */
    public List<BrandDTO> getAllBrands();

    /**
     * <p>
     *     Gets brand By Id
     * </p>
     * @param brandId
     *        id to get the brand
     * @return brand using id
     * @throws CustomException
     *         throws when the brand is not found
     */
    public BrandDTO getBrandById(Long brandId) throws CustomException;

    /**
     * <p>
     *     Gets brand by brand name
     * </p>
     * @param brandName
     *        used to search the brand
     * @return brand using brand name
     * @throws CustomException
     *         throws when the brand is not found
     */
    public BrandDTO getBrandByBrandName(String brandName) throws CustomException;

    /**
     * <p>
     *     updates the brand
     * </p>
     * @param brandDTO
     *        brandDto to be updated as brand
     * @return BrandDto after update
     * @throws CustomException
     *         throws when the brand is not found
     */
    public BrandDTO updateBrand(BrandDTO brandDTO) throws CustomException;

    /**
     * <p>
     *     deletes a brand
     * </p>
     * @param brandId
     *        to get a brand
     * @return brand id if the brand deleted successfully
     * @throws CustomException
     *         throws when the brand is not found
     */
    public Long deleteBrand(Long brandId) throws CustomException;
}
