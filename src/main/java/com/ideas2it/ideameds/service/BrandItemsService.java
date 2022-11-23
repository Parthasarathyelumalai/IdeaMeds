package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.BrandItems;

import java.util.List;

public interface BrandItemsService {

    public BrandItems addBrandItem(BrandItems brandItems);

    public List<BrandItems> getAllBrandItems();

    public BrandItems getBrandItemById(Long brandItemId);

    public BrandItems updateBrandItem(BrandItems brandItems);

    public BrandItems deleteBrandItem(BrandItems brandItems);
}
