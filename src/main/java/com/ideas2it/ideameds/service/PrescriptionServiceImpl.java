package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.converter.PrescriptionConverter;
import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private PrescriptionConverter prescriptionConverter;
    @Override
    public Long addPrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = prescriptionConverter.prescriptionDTOTOPrescription(prescriptionDTO);
        return prescriptionRepository.save(prescription).getPrescriptionId();
    }

    @Override
    public PrescriptionDTO getPrescription(Long prescriptionId) {
        PrescriptionDTO prescriptionDTO = prescriptionConverter.prescriptionTOPrescriptionDTO(prescriptionRepository.findById(prescriptionId).get());
        return prescriptionDTO;
    }

    @Override
    public List<PrescriptionDTO> getPrescriptionByUser(User user) {
        List<PrescriptionDTO> prescriptionDTOs = prescriptionConverter.getAllPrescription(prescriptionRepository.getPrescriptionByUser(user));
        return null;
    }
}
