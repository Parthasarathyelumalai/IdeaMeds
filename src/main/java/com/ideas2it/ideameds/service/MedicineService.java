package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;

import java.util.List;

public interface MedicineService {
    public Medicine addMedicine(Medicine medicine);
    public List<Medicine> getAllMedicines();
    public Medicine getMedicineById(Long medicineId);
    public Medicine updateMedicine(Medicine medicine);
    public Medicine deleteMedicine(long medicineId);
    public Medicine getMedicineByName(String medicineName);
}
