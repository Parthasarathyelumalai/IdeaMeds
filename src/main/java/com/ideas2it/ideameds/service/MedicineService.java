/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;

import java.util.List;

public interface MedicineService {
    public MedicineDTO addMedicine(MedicineDTO medicineDTO);
    public List<Medicine> getAllMedicines();
    public MedicineDTO getMedicineById(Long medicineId);
    public Medicine updateMedicine(Medicine medicine);
    public Medicine deleteMedicine(long medicineId);
    public MedicineDTO getMedicineByName(String medicineName);
}
