package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.BrandItemsDTO;
import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.repository.BrandItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandItemsServiceImpl implements BrandItemsService {

    private final BrandItemsRepository brandItemsRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public BrandItemsServiceImpl(BrandItemsRepository brandItemsRepository) {
        this.brandItemsRepository = brandItemsRepository;
    }

    public BrandItemsDTO addBrandItem(BrandItemsDTO brandItemsDTO) {
        BrandItems brandItems = modelMapper.map(brandItemsDTO, BrandItems.class);
        return modelMapper.map(brandItemsRepository.save(brandItems), BrandItemsDTO.class);

    }

    public List<BrandItems> getAllBrandItems() {
        return brandItemsRepository.findAll();
    }

    public BrandItemsDTO getBrandItemById(Long brandItemId) {
        return modelMapper.map(brandItemsRepository.findById(brandItemId).get(), BrandItemsDTO.class);
    }

    public BrandItems updateBrandItem(BrandItems brandItems) {
        return brandItemsRepository.save(brandItems);
    }

    public BrandItems deleteBrandItem(BrandItems brandItems) {
        brandItems.setDeletedStatus(1);
        return brandItemsRepository.save(brandItems);
    }
}
