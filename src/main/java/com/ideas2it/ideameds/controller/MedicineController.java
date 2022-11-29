package com.ideas2it.ideameds.controller;

import com.ideas2it.ideameds.dto.MedicineDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.service.MedicineService;
import com.ideas2it.ideameds.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * Controller for Medicine details
 * </p>
 *
 * @author Dinesh Kumar R
 * @since 2022-11-18
 * @version 1.0
 */
@RestController
public class MedicineController {
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }


    /**
     * <p>
     *     Adds the medicine
     * </p>
     * @param medicineDTO
     *        new medicine to add
     * @return medicine which was added
     */
    @PostMapping("/medicine")
    public ResponseEntity<MedicineDTO> addMedicine(@Valid @RequestBody MedicineDTO medicineDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.addMedicine(medicineDTO));
    }

    /**
     * <p>
     *     Gets all the medicines
     * </p>
     * @return list of medicines
     * @throws CustomException
     *         throws exception when there is no entry for medicine
     */
    @GetMapping("/medicine")
    public ResponseEntity<List<MedicineDTO>> getAllMedicines() throws CustomException {
        List<MedicineDTO> medicineDTOList = medicineService.getAllMedicines();
        if (medicineDTOList != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(medicineDTOList);
        } else throw new CustomException(Constants.MEDICINE_NOT_FOUND);

    }

    /**
     * <p>
     *     Gets medicine by an id
     * </p>
     * @param medicineId
     *        medicine id for getting medicine
     * @return medicine using the id
     * @throws CustomException
     *         throws exception when there is no medicine found
     */
    @GetMapping("/medicine/{medicineId}")
    public ResponseEntity<MedicineDTO> getMedicineById(@PathVariable("medicineId") Long medicineId) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(medicineService.getMedicineById(medicineId));
    }

    /**
     * <p>
     *     Gets medicine by its name
     * </p>
     * @param medicineName
     *        medicine name for getting medicine
     * @return medicine using the medicine name
     * @throws CustomException
     *         throws exception when there is no medicine found
     */
    @GetMapping("/medicine/getByName/{medicineName}")
    public ResponseEntity<MedicineDTO> getMedicineByName(@PathVariable("medicineName") String medicineName) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(medicineService.getMedicineByName(medicineName));
    }

    /**
     * <p>
     *     Updates the medicine
     * </p>
     * @param medicineDTO
     *        medicine to update
     * @return the updated medicine
     * @throws CustomException
     *         throws exception when there is no medicine found
     */
    @PutMapping("/medicine")
    public ResponseEntity<MedicineDTO> updateMedicine(@Valid @RequestBody MedicineDTO medicineDTO) throws CustomException {
        return ResponseEntity.status(HttpStatus.FOUND).body(medicineService.updateMedicine(medicineDTO));
    }


    /**
     * <p>
     *     Deletes the medicine
     * </p>
     * @param medicineId
     *        corresponding medicine id to delete
     * @return response for deletion
     * @throws CustomException
     *         throws exception when occurs when medicine was not found
     */
    @PutMapping("/medicine/delete/{medicineId}")
    public ResponseEntity<String> deleteMedicine(@PathVariable("medicineId") Long medicineId) throws CustomException {
        Long medicineById = medicineService.deleteMedicine(medicineId);
        if(medicineById != null) {
            return ResponseEntity.status(HttpStatus.OK).body(medicineById + Constants.DELETED_SUCCESSFULLY);
        } else
            return ResponseEntity.status(HttpStatus.OK).body(Constants.NOT_DELETED_SUCCESSFULLY);
    }
}
