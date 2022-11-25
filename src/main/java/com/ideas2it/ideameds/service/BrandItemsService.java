/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the Brand Items
 * </p>
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-21
 */
public interface BrandItemsService {

    /**
     * <p>
     *     Adds brand Item
     * </p>
     * @param brandItemsDTO
     *        Brand Item to add
     * @param medicineDTO
     *        to assign medicine with brand Items
     * @param brandDTO
     *        to assign brand with brand Items
     * @return added Brand Item
     */
    public BrandItemsDTO addBrandItem(BrandItemsDTO brandItemsDTO,
                                      MedicineDTO medicineDTO,
                                      BrandDTO brandDTO);

    /**
     * <p>
     *     Gets all the brand Items
     * </p>
     * @return list of brand Items
     */
    public List<BrandItemsDTO> getAllBrandItems();

    /**
     * <p>
     *     Gets Brand Item by id
     * </p>
     * @param brandItemId
     *        id to get the Brand Item
     * @return Brand Item using brandItemId
     * @throws CustomException
     *         throws when brand item not found
     */
    public BrandItemsDTO getBrandItemById(Long brandItemId) throws CustomException;

    /**
     * <p>
     *     updates the Brand Item
     * </p>
     * @param brandItemsDTO
     *        Brand Item to be updated
     * @return updated Brand Item
     * @throws CustomException
     *         throws when Brand Item Not found
     */
    public BrandItemsDTO updateBrandItem(BrandItemsDTO brandItemsDTO) throws CustomException;

    /**
     * <p>
     *     deletes the brand item
     * </p>
     * @param brandItemId
     *        id to delete the brand item
     * @return Response for deletion
     * @throws CustomException
     *         throws when there is no brand item found
     */
    public Long deleteBrandItem(Long brandItemId) throws CustomException;

    /**
     * <p>
     *     assigns brand item to warehouse
     * </p>
     * @param warehouseDTO
     *        has a warehouse to assign to
     * @param brandItemId
     *        for getting corresponding brand item
     * @return Brand Items After Assigning
     * @throws CustomException
     *         throws when there is no brand item found
     */
    public BrandItemsDTO assignToWarehouse(WarehouseDTO warehouseDTO, Long brandItemId) throws CustomException;

    /**
     * <p>
     *     Gets brand items by medicine name or a partial word
     * </p>
     * @param medicineName
     *        medicine name or a word to get brand items
     * @return list of brand items using medicineName
     * @throws CustomException
     *         throws when there is no brand item found
     */
    List<BrandItemsDTO> getByMedicineName(String medicineName) throws CustomException;
}
