package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandDTO;
import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.dto.WarehouseDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.BrandItemsService;
import com.ideas2it.ideameds.service.BrandService;
import com.ideas2it.ideameds.service.MedicineService;
import com.ideas2it.ideameds.service.WarehouseService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Brand Items
 *
 * @author Dinesh Kumar R
 * @since 23/11/2022
 * @version 1.0
 */
@RestController
public class BrandItemsController {

    private final BrandItemsService brandItemsService;
    private final WarehouseService warehouseService;
    private final BrandService brandService;
    private final MedicineService medicineService;

    public BrandItemsController(BrandItemsService brandItemsService,
                                WarehouseService warehouseService,
                                BrandService brandService,
                                MedicineService medicineService) {
        this.brandItemsService = brandItemsService;
        this.warehouseService = warehouseService;
        this.brandService = brandService;
        this.medicineService = medicineService;
    }

    /**
     * <p>
     *     add brand item
     * </p>
     * @param brandItemsDTO
     *        brand item to add
     * @param medicineId
     *        to get medicine to assign
     * @param brandId
     *        to get brand to assign
     * @return added brand item
     */
    @PostMapping("/brandItems/{medicineId}/{brandId}")
    public ResponseEntity<BrandItemsDTO> addBrandItem(@RequestBody BrandItemsDTO brandItemsDTO,
                                      @PathVariable("medicineId") Long medicineId,
                                      @PathVariable("brandId") Long brandId) throws CustomException {
        MedicineDTO medicineDTO = medicineService.getMedicineById(medicineId);
        BrandDTO brandDTO = brandService.getBrandById(brandId);
        return ResponseEntity.status(HttpStatus.OK).body(brandItemsService
                .addBrandItem(brandItemsDTO, medicineDTO, brandDTO));
    }

    /**
     * <p>
     *     gets all brand items
     * </p>
     * @return List of brand items
     */
    @GetMapping("/brandItems")
    public ResponseEntity<List<BrandItemsDTO>> getAllBrandItems() {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getAllBrandItems());
    }

    /**
     * <p>
     *     gets brand item by id
     * </p>
     * @param brandItemsId
     *        contains an id to get a brand item
     * @return brand item using the id
     * @throws CustomException
     *         throws when the brand item not found
     */
    @GetMapping("/brandItem/{brandItemsId}")
    public ResponseEntity<BrandItemsDTO> getBrandItemById(@PathVariable("brandItemsId") Long brandItemsId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getBrandItemById(brandItemsId));
    }

    /**
     * <p>
     *     updates brand item
     * </p>
     * @param brandItemsDTO
     *        brand item to be updated
     * @return updated brand item
     * @throws CustomException
     *         throws when brand item not found
     */
    @PutMapping("/brandItems")
    public ResponseEntity<BrandItemsDTO> updateBrandItem(@RequestBody BrandItemsDTO brandItemsDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemsService.updateBrandItem(brandItemsDTO));
    }

    /**
     * <p>
     *     Assigns brand Item to warehouses
     * </p>
     * @param warehouseId
     *        contains list of warehouses to assign
     * @param brandItemId
     *        to get brand item by id
     * @return brand item after assigning
     * @throws CustomException
     *         throws when warehouse not found and also
     *         throws when brand item not found
     */
    @PutMapping("/assignBrandItems/{brandItemId}/{warehouseId}")
        public ResponseEntity<BrandItemsDTO> assignToWarehouse(@PathVariable("warehouseId") Long warehouseId,
                                        @PathVariable("brandItemId") Long brandItemId) throws CustomException {
        WarehouseDTO warehouseDTO = warehouseService.getWarehouseById(warehouseId);
        if (warehouseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(brandItemsService.assignToWarehouse(warehouseDTO, brandItemId));
        } else throw new CustomException(Constants.WAREHOUSE_NOT_FOUND);
    }

    /**
     * <p>
     *     Deletes the brand Item
     * </p>
     * @param brandItemId
     *        id to delete the corresponding brand Item
     * @return response for deletion
     * @throws CustomException
     *         throws when brand item not found
     */
    @PutMapping("/brandItems/delete/{brandItemId}")
    public ResponseEntity<String> deleteBrandItem(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        Long brandItemsById = brandItemsService.deleteBrandItem(brandItemId);
        if(brandItemsById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandItemsById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }

    /**
     * <p>
     *     Gets brand items by medicine name or a partial word
     * </p>
     * @param medicineName
     *        medicine name or a word to get brand items
     * @return list of brand items using user input
     * @throws CustomException
     *         throws when brand item not found
     */
    @GetMapping("/medicineName/{medicineName}")
    public ResponseEntity<List<BrandItemsDTO>> getByMedicineName(@PathVariable String medicineName) throws CustomException {
        List<BrandItemsDTO> brandItemsDTOs = brandItemsService.getByMedicineName(medicineName);
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsDTOs);
    }

}
