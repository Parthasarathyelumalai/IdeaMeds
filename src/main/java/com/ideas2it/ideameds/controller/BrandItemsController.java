package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.service.BrandItemsService;
import com.ideas2it.ideameds.service.WarehouseService;
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

    public BrandItemsController(BrandItemsService brandItemsService, WarehouseService warehouseService) {
        this.brandItemsService = brandItemsService;
        this.warehouseService = warehouseService;
    }

    /**
     * <p>
     *     add brand item
     * </p>
     * @param brandItems
     *        brand item to add
     * @return added brand item
     */
    @PostMapping("/brandItems")
    public BrandItems addBrandItem(@RequestBody BrandItems brandItems) {
        return brandItemsService.addBrandItem(brandItems);
    }

    /**
     * <p>
     *     gets brand item by id
     * </p>
     * @param brandItemsId
     *        contains an id to get a brand item
     * @return brand item using the id
     */
    @GetMapping("/brandItem/{brandItemsId}")
    public BrandItems getBrandItemById(@PathVariable("brandItemsId") Long brandItemsId) {
        return brandItemsService.getBrandItemById(brandItemsId);
    }

    /**
     * <p>
     *     gets all brand items
     * </p>
     * @return List of brand items
     */
    @GetMapping("/brandItems")
    public List<BrandItems> getAllBrandItems() {
        return brandItemsService.getAllBrandItems();
    }

    /**
     * <p>
     *     updates brand item
     * </p>
     * @param brandItems
     *        brand item to be updated
     * @return updated brand item
     */
    @PutMapping("/brandItems")
    public BrandItems updateBrandItem(@RequestBody BrandItems brandItems) {
        return brandItemsService.updateBrandItem(brandItems);
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
     */
    @PutMapping("/assignBrandItems/{brandItemId}/{warehouseId}")
        public BrandItems assignToWarehouse(@PathVariable("warehouseId") Long warehouseId,
                                        @PathVariable("brandItemId") Long brandItemId) {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        return brandItemsService.assignToWarehouse(warehouse, brandItemId);
    }

}
