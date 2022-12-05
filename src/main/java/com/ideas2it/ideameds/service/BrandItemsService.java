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
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-21
 */
public interface BrandItemsService {

    /**
     * <p>
     * Adds brand Item
     * </p>
     *
     * @param brandItemsDTO Brand Item to add
     * @param medicineDTO   to assign medicine with brand Items
     * @param brandDTO      to assign brand with brand Items
     * @return added Brand Item
     */
    BrandItemsDTO addBrandItem(BrandItemsDTO brandItemsDTO,
                               MedicineDTO medicineDTO,
                               BrandDTO brandDTO);

    /**
     * <p>
     * Gets all the brand Items
     * </p>
     *
     * @return list of brand Items
     */
    List<BrandItemsDTO> getAllBrandItems();

    /**
     * <p>
     * Gets Brand Item by id
     * </p>
     *
     * @param brandItemId id to get the Brand Item
     * @return Brand Item using brandItemId
     * @throws CustomException throws when brand item not found
     */
    BrandItemsDTO getBrandItemById(Long brandItemId) throws CustomException;

    /**
     * <p>
     * Gets brand using brandItems id
     * </p>
     *
     * @param brandItemId brand item id to get a brand Item
     * @return brand from brand Item
     * @throws CustomException throws when brand Items not found and
     *                         throws when brand not found
     */
    BrandDTO getBrandByBrandItemId(Long brandItemId) throws CustomException;

    /**
     * <p>
     * Gets medicine using brandItems id
     * </p>
     *
     * @param brandItemId brand item id to get a brand Item
     * @return medicine from brand Item
     * @throws CustomException throws when brand Items not found and
     *                         throws when medicine not found
     */
    MedicineDTO getMedicineByBrandItemId(Long brandItemId) throws CustomException;

    /**
     * <p>
     * updates the Brand Item
     * </p>
     *
     * @param brandItemsDTO Brand Item to be updated
     * @return updated Brand Item
     * @throws CustomException throws when Brand Item Not found
     */
    BrandItemsDTO updateBrandItem(BrandItemsDTO brandItemsDTO) throws CustomException;

    /**
     * <p>
     * deletes the brand item
     * </p>
     *
     * @param brandItemId id to delete the brand item
     * @return Response for deletion
     * @throws CustomException throws when there is no brand item found
     */
    Long deleteBrandItem(Long brandItemId) throws CustomException;

    /**
     * <p>
     * assigns brand item to warehouse
     * </p>
     *
     * @param warehouseDTO has a warehouse to assign to
     * @param brandItemId  for getting corresponding brand item
     * @return Brand Items After Assigning
     * @throws CustomException throws when there is no brand item found
     */
    BrandItemsDTO assignToWarehouse(WarehouseDTO warehouseDTO, Long brandItemId) throws CustomException;

    /**
     * <p>
     * Gets brand items by medicine name or a partial word
     * </p>
     *
     * @param medicineName medicine name or a word to get brand items
     * @return list of brand items using medicineName
     * @throws CustomException throws when there is no brand item found
     */
    List<BrandItemsDTO> getByMedicineName(String medicineName) throws CustomException;

    /**
     * <p>
     * Gets brand Item By brand Item Name
     * </p>
     *
     * @param brandItemName name to get brand item
     * @return brand item using brand item name
     * @throws CustomException throws when Brand Item is not found
     */
    BrandItemsDTO getBrandItemByName(String brandItemName) throws CustomException;

    /**
     * <p>
     * Gets brand Item By brand Item Name
     * To get Brand item for prescription
     * </p>
     *
     * @param brandItemName name to get brand item
     * @return brand item using brand item name
     */
    BrandItemsDTO getBrandItemByBrandItemName(String brandItemName);
}
