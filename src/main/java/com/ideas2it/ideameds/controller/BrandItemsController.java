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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Brand Items
 *
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-22
 */
@RestController
public class BrandItemsController {

    private final BrandItemsService brandItemsService;
    private final WarehouseService warehouseService;
    private final BrandService brandService;
    private final MedicineService medicineService;

    /**
     * <p>
     * Constructs a new object
     * </p>
     *
     * @param brandItemsService creates new instance for brand Items Service
     * @param warehouseService  creates new instance for warehouse service
     * @param brandService      creates new instance for brand service
     * @param medicineService   creates new instance for medicine service
     */
    @Autowired
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
     * add brand item
     * </p>
     *
     * @param brandItemsDTO brand item to add
     * @param medicineId    to get medicine to assign
     * @param brandId       to get brand to assign
     * @return added brand item
     * @throws CustomException throws when the brand not found and
     *                         throws when the medicine not found
     *                         throws when the new brand item name already exist
     */
    @PostMapping("/brand-items/{medicineId}/{brandId}")
    public ResponseEntity<BrandItemsDTO> addBrandItem(@Valid @RequestBody BrandItemsDTO brandItemsDTO,
                                                      @PathVariable("medicineId") Long medicineId,
                                                      @PathVariable("brandId") Long brandId) throws CustomException {
        MedicineDTO medicineDTO = medicineService.getMedicineById(medicineId);
        BrandDTO brandDTO = brandService.getBrandById(brandId);
        return ResponseEntity.status(HttpStatus.OK).body(brandItemsService
                .addBrandItem(brandItemsDTO, medicineDTO, brandDTO));
    }

    /**
     * <p>
     * gets all brand items
     * </p>
     *
     * @return List of brand items
     */
    @GetMapping("/brand-items/get-all")
    public ResponseEntity<List<BrandItemsDTO>> getAllBrandItems() {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getAllBrandItems());
    }

    /**
     * <p>
     * gets brand item by id
     * </p>
     *
     * @param brandItemsId contains an id to get a brand item
     * @return brand item using the id
     * @throws CustomException throws when the brand item not found
     */
    @GetMapping("/brand-items/{brandItemsId}")
    public ResponseEntity<BrandItemsDTO> getBrandItemById(@PathVariable("brandItemsId") Long brandItemsId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getBrandItemById(brandItemsId));
    }

    /**
     * <p>
     * Gets brand Items with brand Item Name
     * </p>
     *
     * @param brandItemName name to get Brand Item
     * @return brand Item using brand Item Name
     * @throws CustomException throws when brand item not found
     */
    @GetMapping("/brand-items/by-name/{brandItemsName}")
    public ResponseEntity<BrandItemsDTO> getBrandItemByName(@PathVariable("brandItemsName") String brandItemName) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getBrandItemByName(brandItemName));
    }

    /**
     * <p>
     * Gets list of brand items related with medicine name or a partial word
     * </p>
     *
     * @param medicineName medicine name or a word to get brand items
     * @return list of brand items using user input
     * @throws CustomException throws when brand item not found
     */
    @GetMapping("/medicine-name/{medicineName}")
    public ResponseEntity<List<BrandItemsDTO>> getByMedicineName(@PathVariable String medicineName) throws CustomException {
        List<BrandItemsDTO> brandItemsDTOs = brandItemsService.getByMedicineName(medicineName);
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsDTOs);
    }

    /**
     * <p>
     * Gets brand using brand Items Id
     * </p>
     *
     * @param brandItemsId Id to get brand Items
     * @return brand from brand Items
     * @throws CustomException throws when brand Item not found and
     *                         throws when brand not found
     */
    @GetMapping("/brand-items/getBrand/{brandItemsId}")
    public ResponseEntity<BrandDTO> getBrandByBrandItemId(@PathVariable("brandItemsId") Long brandItemsId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getBrandByBrandItemId(brandItemsId));
    }

    /**
     * <p>
     * Gets medicine using brand Items Id
     * </p>
     *
     * @param brandItemsId Id to get brand Items
     * @return medicine from brand Items
     * @throws CustomException throws when brand Item not found and
     *                         throws when medicine not found
     */
    @GetMapping("/brand-items/get-medicine/{brandItemId}")
    public ResponseEntity<MedicineDTO> getMedicineByBrandItemId(@PathVariable("brandItemId") Long brandItemsId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(brandItemsService.getMedicineByBrandItemId(brandItemsId));
    }

    /**
     * <p>
     * updates brand item
     * </p>
     *
     * @param brandItemsDTO brand item to be updated
     * @return updated brand item
     * @throws CustomException throws when brand item not found
     */
    @PutMapping("/brand-items")
    public ResponseEntity<BrandItemsDTO> updateBrandItem(@Valid @RequestBody BrandItemsDTO brandItemsDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(brandItemsService.updateBrandItem(brandItemsDTO));
    }

    /**
     * <p>
     * Assigns brand Item to warehouses
     * </p>
     *
     * @param warehouseId contains list of warehouses to assign
     * @param brandItemId to get brand item by id
     * @return brand item after assigning
     * @throws CustomException throws when warehouse not found and also
     *                         throws when brand item not found
     */
    @PutMapping("/brand-items/assign/{brandItemId}/{warehouseId}")
    public ResponseEntity<BrandItemsDTO> assignToWarehouse(@PathVariable("warehouseId") Long warehouseId,
                                                           @PathVariable("brandItemId") Long brandItemId) throws CustomException {
        WarehouseDTO warehouseDTO = warehouseService.getWarehouseById(warehouseId);
        if (warehouseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(brandItemsService.assignToWarehouse(warehouseDTO, brandItemId));
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.WAREHOUSE_NOT_FOUND);
    }

    /**
     * <p>
     * Deletes the brand Item
     * </p>
     *
     * @param brandItemId id to delete the corresponding brand Item
     * @return response for deletion
     * @throws CustomException throws when brand item not found
     */
    @PutMapping("/brand-items/delete/{brandItemId}")
    public ResponseEntity<String> deleteBrandItem(@PathVariable("brandItemId") Long brandItemId) throws CustomException {
        Long brandItemsById = brandItemsService.deleteBrandItem(brandItemId);
        if (brandItemsById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(brandItemsById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}