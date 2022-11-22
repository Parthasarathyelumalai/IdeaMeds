package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Brand;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
    public Medicine getMedicineById(Long medicineId) {
        return medicineRepository.findById(medicineId).get();
    }
    public Medicine getMedicineByName(String medicineName) {
        return medicineRepository.getMedicineByMedicineName(medicineName);
    }

    public Medicine updateMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    public Medicine deleteMedicine(long medicineId) {
        Medicine medicine = getMedicineById(medicineId);
        medicine.setDeletedStatus(1);
        return medicineRepository.save(medicine);
    }
}
