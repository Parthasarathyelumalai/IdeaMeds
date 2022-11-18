package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }
    public Medicine getMedicineById(Long medicineId) {
        return medicineRepository.findById(medicineId).get();
    }
    public Medicine updateMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    public Medicine deleteMedicine(long medicineId) {
        Medicine medicine = getMedicineById(medicineId);
        medicine.setDeletedStatus(true);
        return medicineRepository.save(medicine);
    }
}
