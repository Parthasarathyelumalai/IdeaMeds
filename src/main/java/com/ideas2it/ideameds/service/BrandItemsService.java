package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.model.BrandItems;

import java.util.List;

public interface BrandItemsService {

    public BrandItemsDTO addBrandItem(BrandItemsDTO brandItemsDTO);

    public List<BrandItems> getAllBrandItems();

    public BrandItemsDTO getBrandItemById(Long brandItemId);

    public BrandItems updateBrandItem(BrandItems brandItems);

    public BrandItems deleteBrandItem(BrandItems brandItems);
}
