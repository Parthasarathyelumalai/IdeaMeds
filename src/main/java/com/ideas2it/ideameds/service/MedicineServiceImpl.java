/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for Medicine
 * @author Dinesh Kumar R
 * @version 1.0
 * @since 2022-11-18
 */
@Service
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     *{@inheritDoc}
     */
    public MedicineDTO addMedicine(MedicineDTO medicineDTO) {
        Medicine medicine = modelMapper.map(medicineDTO, Medicine.class);
        medicine.setCreatedAt(DateTimeValidation.getDate());
        medicine.setModifiedAt(DateTimeValidation.getDate());
        return modelMapper.map(medicineRepository.save(medicine), MedicineDTO.class);
    }

    /**
     *{@inheritDoc}
     */
    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll()
                .stream().map(medicine -> modelMapper.
                        map(medicine, MedicineDTO.class)).toList();
    }

    /**
     *{@inheritDoc}
     */
    public MedicineDTO getMedicineById(Long medicineId) throws CustomException {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        if(medicine.isPresent()) {
            return modelMapper.map(medicine, MedicineDTO.class);
        } else throw new CustomException(Constants.MEDICINE_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public MedicineDTO getMedicineByName(String medicineName) throws CustomException {
        Medicine medicine = medicineRepository.getMedicineByMedicineName(medicineName);
        if(medicine != null) {
            return modelMapper.map(medicine, MedicineDTO.class);
        } else throw new CustomException(Constants.MEDICINE_NOT_FOUND);
    }

    /**
     *{@inheritDoc}
     */
    public MedicineDTO updateMedicine(MedicineDTO medicineDTO) throws CustomException {
        Medicine medicine = modelMapper.map(medicineDTO, Medicine.class);
        Optional<Medicine> existMedicine = medicineRepository.findById(medicineDTO.getMedicineId());
        if(existMedicine.isEmpty()) {
            throw new CustomException(Constants.MEDICINE_NOT_FOUND);
        }
        medicine.setCreatedAt(existMedicine.get().getCreatedAt());
        medicine.setModifiedAt(DateTimeValidation.getDate());
        return modelMapper.map(medicineRepository.save(medicine), MedicineDTO.class);
    }

    /**
     *{@inheritDoc}
     */
    public Long deleteMedicine(Long medicineId) throws CustomException {
        Optional<Medicine> medicine = medicineRepository.findById(medicineId);
        if (medicine.isPresent()) {
            medicine.get().setDeletedStatus(1);
            medicine.get().setModifiedAt(DateTimeValidation.getDate());
        return medicineRepository.save(medicine.get()).getMedicineId();
        } else throw new CustomException(Constants.MEDICINE_NOT_FOUND);
    }
}
