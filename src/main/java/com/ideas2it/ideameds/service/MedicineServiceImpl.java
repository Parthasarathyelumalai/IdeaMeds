/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.MedicineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public MedicineDTO addMedicine(MedicineDTO medicineDTO) {
        Medicine medicine = modelMapper.map(medicineDTO, Medicine.class);
        return modelMapper.map(medicineRepository.save(medicine), MedicineDTO.class);
    }
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
    public MedicineDTO getMedicineById(Long medicineId) {
        return modelMapper.map(medicineRepository.findById(medicineId).get(), MedicineDTO.class);
    }
    public MedicineDTO getMedicineByName(String medicineName) {
        return modelMapper.map(medicineRepository.getMedicineByMedicineName(medicineName), MedicineDTO.class);
    }

    public Medicine updateMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    public Medicine deleteMedicine(long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId).get();
        medicine.setDeletedStatus(1);
        return medicineRepository.save(medicine);
    }
}
