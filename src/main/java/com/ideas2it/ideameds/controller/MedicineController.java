package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Medicine details
 *
 * @author - Dinesh Kumar R
 * @since - 18/11/2022
 * @version - 1.0
 */
@RestController
@RequestMapping("/ideameds")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/medicine")
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.addMedicine(medicine);
    }

    @GetMapping("/medicine")
    public List<Medicine> getAllMedicine() {
        return medicineService.getAllMedicine();
    }

    @GetMapping("/medicine/{medicineId}")
    public Medicine getMedicineById(@PathVariable("medicineId") Long medicineId) {
        return medicineService.getMedicineById(medicineId);
    }

    @PutMapping("/medicine")
    public Medicine updateMedicine(@RequestBody Medicine medicine) {
        return medicineService.updateMedicine(medicine);
    }

    @PutMapping("/medicine/{medicineId}")
    public Medicine deleteMedicine(@PathVariable("medicineId") Long medicineId) {
        return medicineService.deleteMedicine(medicineId);
    }
}
