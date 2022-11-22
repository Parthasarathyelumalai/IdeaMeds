package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.model.BrandItems;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.service.BrandItemsService;
import com.ideas2it.ideameds.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Controller for Medicine details
 * </p>
 *
 * @author Dinesh Kumar R
 * @since 18/11/2022
 * @version 1.0
 */
@RestController
public class MedicineController {
    private final MedicineService medicineService;
    private final BrandItemsService brandItemsService;

    public MedicineController(MedicineService medicineService, BrandItemsService brandItemsService) {
        this.medicineService = medicineService;
        this.brandItemsService = brandItemsService;
    }


    /**
     * <p>
     *     Adds the medicine
     * </p>
     * @param medicine
     *        new medicine to add
     * @return medicine which was added
     */
    @PostMapping("/medicine")
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.addMedicine(medicine);
    }

    /**
     * <p>
     *     Gets all the medicines
     * </p>
     * @return list of medicines
     */
    @GetMapping("/medicine")
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    /**
     * <p>
     *     Gets medicine by an id
     * </p>
     * @param medicineId
     *        medicine id for getting medicine
     * @return medicine using the id
     */
    @GetMapping("/medicine/{medicineId}")
    public Medicine getMedicineById(@PathVariable("medicineId") Long medicineId) {
        return medicineService.getMedicineById(medicineId);
    }

    /**
     * <p>
     *     Gets medicine by its name
     * </p>
     * @param medicineName
     *        medicine name for getting medicine
     * @return medicine using the medicine name
     */
    @GetMapping("/medicine/getByName/{medicineName}")
    public Medicine getMedicineByName(@PathVariable("medicineName") String medicineName) {
        return medicineService.getMedicineByName(medicineName);
    }

    /**
     * <p>
     *     Updates the medicine
     * </p>
     * @param medicine
     *        medicine to update
     * @return the updated medicine
     */
    @PutMapping("/medicine")
    public Medicine updateMedicine(@RequestBody Medicine medicine) {
        return medicineService.updateMedicine(medicine);
    }


    /**
     * <p>
     *     Deletes the medicine
     * </p>
     * @param medicineId
     *        corresponding medicine id to delete
     * @return response for deletion
     */
    @PutMapping("/medicine/delete/{medicineId}")
    public Medicine deleteMedicine(@PathVariable("medicineId") Long medicineId) {
        Medicine medicine = medicineService.getMedicineById(medicineId);
        List<BrandItems> brandItems = medicine.getBrandItems();
        for (BrandItems brandItem: brandItems) {
            brandItemsService.deleteBrandItem(brandItem);
        }
        return medicineService.deleteMedicine(medicineId);
    }
}
