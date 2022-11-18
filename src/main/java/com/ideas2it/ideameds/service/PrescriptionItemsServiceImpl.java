package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.converter.PrescriptionConverter;
import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.repository.PrescriptionItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionItemsServiceImpl implements PrescriptionItemsService{
    @Autowired
    private PrescriptionItemsRepository prescriptionItemsRepository;
    @Autowired
    private PrescriptionConverter prescriptionConverter;
    @Override
    public Long addPrescriptionItems(PrescriptionItems prescriptionItems, PrescriptionDTO prescriptionDTO) {
        Prescription prescription = prescriptionConverter.prescriptionDTOTOPrescription(prescriptionDTO);
        prescriptionItems.setPrescription(prescription);
        return prescriptionItemsRepository.save(prescriptionItems).getPrescriptionItemsId();
    }
}
