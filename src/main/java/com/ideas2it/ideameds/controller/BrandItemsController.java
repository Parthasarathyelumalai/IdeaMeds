package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.service.BrandItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandItemsController {

    private final BrandItemsService brandItemsService;

    public BrandItemsController(BrandItemsService brandItemsService) {
        this.brandItemsService = brandItemsService;
    }

    @PostMapping("/brandItems")
    public BrandItems addBrandItem(@RequestBody BrandItems brandItems) {
        return brandItemsService.addBrandItem(brandItems);
    }

    @GetMapping("/brandItems")
    public List<BrandItems> getAllBrandItems() {
        return brandItemsService.getAllBrandItems();
    }

    @PutMapping("/brandItems")
    public BrandItems updateBrandItem(@RequestBody BrandItems brandItems) {
        return brandItemsService.updateBrandItem(brandItems);
    }

}
