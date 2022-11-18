package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Medicine;

import java.util.List;

public interface MedicineService {
    public Medicine addMedicine(Medicine medicine);
    public List<Medicine> getAllMedicine();
    public Medicine getMedicineById(Long medicineId);
    public Medicine updateMedicine(Medicine medicine);
    public Medicine deleteMedicine(long medicineId);
}
