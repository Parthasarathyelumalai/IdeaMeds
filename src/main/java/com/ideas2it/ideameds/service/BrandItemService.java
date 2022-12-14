/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.BrandItem;

import java.util.List;

/**
 * <p>
 * Service Interface
 * Performs Create, Read, Update and Delete operations for the brand item
 * </p>
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-21
 */
public interface BrandItemService {

    /**
     * Adds the new brand item record to the database.
     * Each entry contains validated entries
     * brand item is a singular record for a unit in medicine
     * A brand item has association with both medicine and brand
     *
     * @param brandItemDTO Brand Item to add
     * @param medicineDTO   to assign medicine with brand item
     * @param brandDTO      to assign brand with brand item
     * @return added Brand Item
     * @throws CustomException
     *         throws when the new brand item name already exist
     */
    BrandItemDTO addBrandItem(BrandItemDTO brandItemDTO,
                              MedicineDTO medicineDTO,
                              BrandDTO brandDTO) throws CustomException;

    /**
     * Gets all the brandItemsDTOs available in the database
     *
     * @return list of all brand item if it's available in the database
     *         null if there is no record
     */
    List<BrandItemDTO> getAllBrandItems();

    /**
     * Gets brand Item Dto using the requested id
     * The id from controller should be exact same compared with
     * brand Item id from the database
     *
     * @param brandItemId used to search through and get a valid brand Item
     * @return brand Item after the id gets a valid brand item available in the database
     * @throws CustomException throws when the brand item not found using the id from the controller
     */
    BrandItemDTO getBrandItemDTOById(Long brandItemId) throws CustomException;

    /**
     * Gets brand item using the requested id
     * The id from controller should be exact same compared with
     * brand item id from the database
     *
     * @param brandItemId used to search through and get a valid brand Item
     * @return brand Item after the id gets a valid brand item available in the database
     * @throws CustomException throws when the brand item not found using the id from the controller
     */
    BrandItem getBrandItemById(Long brandItemId) throws CustomException;

    /**
     * Gets a brand item DTO by name
     * A brand Item name from the controller should be exact same
     * compared with brand item name available in the database.
     *
     * @param brandItemName The name of the brand item you want to get.
     * @return A brand item by name when the name matches a
     *         brand item name from the database
     * @throws CustomException throws when brand item not found
     *                         using the brand Item name from the request
     */
    BrandItemDTO getBrandItemByName(String brandItemName) throws CustomException;

    /**
     * Gets list of brand item related with medicine name
     * or a partial word from the request
     * A brand Item name can be similar or exact compared with the
     * name available in the database.
     *
     * @param medicineName medicine name or a word to get brand item
     * @return list of brand item using medicineName
     * @throws CustomException throws when there is no brand item found
     */
    List<BrandItemDTO> getBrandItemBySearch(String medicineName) throws CustomException;

    /**
     * This function is used to get the brand of a brand item by its id
     * A Brand Item id should be exact same compared with brand Item id
     * from the database
     *
     * @param brandItemId brand item id to get a brand Item
     * @return brand from brand item after getting the brand item
     * @throws CustomException throws when brand Item using the id not found and
     *                         throws when brand from the brand item not found
     */
    BrandDTO getBrandDTOByBrandItemId(Long brandItemId) throws CustomException;

    /**
     * This function is used to get the medicine details by brand item id
     * A Brand Item id should be exact same compared with brand id
     * from the database
     *
     * @param brandItemId brand item id to get a brand Item
     * @return medicine from brand item after getting the brand item
     * @throws CustomException throws when brand Item using the id not found and
     *                         throws when medicine from the brand item not found
     */
    MedicineDTO getMedicineDTOByBrandItemId(Long brandItemId) throws CustomException;

    /**
     * Updates the existing brand Item in the database
     * The brand item will be found by the id and gets updated
     * Update process requires a valid brand item, to ensure it, each entry
     * have validation.
     * Changing a brand item ID to invalid format throws exception
     *
     * @param brandItemDTO The validated Updated Brand Item to be updated
     * @return Brand Item Dto after it was updated successfully
     * @throws CustomException throws when the brand was not found, happens when the brand item ID
     *                         was changed before updating
     */
    BrandItemDTO updateBrandItem(BrandItemDTO brandItemDTO) throws CustomException;

    /**
     * Assigns brand Item to the corresponding warehouse,
     * in which warehouse holds the collection of brand item and
     * brand item hold collections of warehouse available information.
     * Brand Item record can be assigned to a corresponding
     * warehouse one at a time.
     *
     * @param warehouseId contains warehouse Id to get warehouse and assign a brand item to it
     * @param brandItemId  to get brand item by id and assign it to warehouse
     * @return brand item After successfully Assigning
     * @throws CustomException throws when there is no brand item found
     */
    BrandItemDTO assignToWarehouse(Long warehouseId, Long brandItemId) throws CustomException;

    /**
     * Soft deletes the brand item using the corresponding id.
     * brand id from the request will be used to get the brand
     * and will be flagged as deleted.
     *
     * @param brandItemId id to delete the corresponding brand Item
     * @return brand item after it has been flagged deleted
     * @throws CustomException throws when brand item not found using the
     *                         corresponding brand item id
     */
    Long deleteBrandItem(Long brandItemId) throws CustomException;
}
