package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Warehouse;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandItemsServiceImpl implements BrandItemsService {

    private final BrandItemsRepository brandItemsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public BrandItemsServiceImpl(BrandItemsRepository brandItemsRepository) {
        this.brandItemsRepository = brandItemsRepository;
    }

    @Override
    public BrandItems addBrandItem(BrandItems brandItems) {
        return brandItemsRepository.save(brandItems);

    }

    @Override
    public List<BrandItems> getAllBrandItems() {
        return brandItemsRepository.findAll();
    }

    @Override
    public BrandItems getBrandItemById(Long brandItemId) {
        return brandItemsRepository.findById(brandItemId).get();
    }

    @Override
    public BrandItems updateBrandItem(BrandItems brandItems) {
        return brandItemsRepository.save(brandItems);
    }

    @Override
    public BrandItems deleteBrandItem(BrandItems brandItems) {
        brandItems.setDeletedStatus(1);
        return brandItemsRepository.save(brandItems);
    }

    @Override
    public BrandItems assignToWarehouse(Warehouse warehouse, Long brandItemId) {
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);
        BrandItems brandItems = brandItemsRepository.findById(brandItemId).get();
        brandItems.setWarehouses(warehouses);
        return brandItemsRepository.save(brandItems);
    }
}
