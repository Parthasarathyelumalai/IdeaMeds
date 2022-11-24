/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Warehouse;

import java.util.List;

public interface BrandItemsService {

    public BrandItems addBrandItem(BrandItems brandItems);

    public List<BrandItems> getAllBrandItems();

    public BrandItems getBrandItemById(Long brandItemId);

    public BrandItems updateBrandItem(BrandItems brandItems);

    public BrandItems deleteBrandItem(BrandItems brandItems);

    public BrandItems assignToWarehouse(Warehouse warehouse, Long brandItemId);
}
