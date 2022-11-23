package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
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
    public BrandItemsDTO addBrandItem(@RequestBody BrandItemsDTO brandItemsDTO) {
        return brandItemsService.addBrandItem(brandItemsDTO);
    }

    @GetMapping("/brandItem/{brandItemsId}")
    public BrandItemsDTO getBrandItemById(@PathVariable("brandItemsId") Long brandItemsId) {
        return brandItemsService.getBrandItemById(brandItemsId);
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
