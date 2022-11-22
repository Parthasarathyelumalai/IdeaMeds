package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandItemsServiceImpl implements BrandItemsService {

    private final BrandItemsRepository brandItemsRepository;

    public BrandItemsServiceImpl(BrandItemsRepository brandItemsRepository) {
        this.brandItemsRepository = brandItemsRepository;
    }

    public BrandItems addBrandItem(BrandItems brandItems) {
        return brandItemsRepository.save(brandItems);

    }

    public List<BrandItems> getAllBrandItems() {
        return brandItemsRepository.findAll();
    }

    public BrandItems updateBrandItem(BrandItems brandItems) {
        return brandItemsRepository.save(brandItems);
    }

    public BrandItems deleteBrandItem(BrandItems brandItems) {
        brandItems.setDeletedStatus(1);
        return brandItemsRepository.save(brandItems);
    }
}
