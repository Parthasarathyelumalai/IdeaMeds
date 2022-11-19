package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService{
    ModelMapper modelMapper = new ModelMapper();
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;

    @Override
    public Long addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription).getPrescriptionId();
    }

    @Override
    public PrescriptionDTO getPrescription(Long prescriptionId) {
        return modelMapper.map(prescriptionRepository.findById(prescriptionId).get(),PrescriptionDTO.class);
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionByUser(User user) {
        //  List<PrescriptionDTO> prescriptionDTOs = prescriptionMapper.getAllPrescription(prescriptionRepository.getPrescriptionByUser(user));
        return null;
    }

    @Override
    public void addToCart(List<PrescriptionItems> prescriptionItems) {
        List<Medicine> medicineList = new ArrayList<>();
        List<Medicine> medicinesOutOfStock = new ArrayList<>();
        if(prescriptionItems != null){
            List<Medicine> medicines = medicineRepository.findAll();
            for(PrescriptionItems prescriptionItem : prescriptionItems) {
                for(Medicine medicine : medicines) {
                    if(medicine.getMedicineName().equals(prescriptionItem.getMedicineName())) {
                        medicineList.add(medicine);
                    } else medicinesOutOfStock.add(medicine);
                }
            }
        }
    }
}