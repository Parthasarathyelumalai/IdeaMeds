package com.ideas2it.ideameds.converter;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.Prescription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionConverter {
    public Prescription prescriptionDTOTOPrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(prescriptionDTO.getPrescriptionId());
        prescription.setClinicAddress(prescriptionDTO.getClinicAddress());
        prescription.setDateOfIssue(prescriptionDTO.getDateOfIssue());
        prescription.setDoctorName(prescriptionDTO.getDoctorName());
        prescription.setPatientAge(prescriptionDTO.getPatientAge());
        prescription.setPatientGender(prescriptionDTO.getPatientGender());
        prescription.setPatientName(prescriptionDTO.getPatientName());
        return prescription;
    }

    public PrescriptionDTO prescriptionTOPrescriptionDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPrescriptionId(prescription.getPrescriptionId());
        prescriptionDTO.setClinicAddress(prescription.getClinicAddress());
        prescriptionDTO.setDateOfIssue(prescription.getDateOfIssue());
        prescriptionDTO.setDoctorName(prescription.getDoctorName());
        prescriptionDTO.setPatientAge(prescription.getPatientAge());
        prescriptionDTO.setPatientGender(prescription.getPatientGender());
        prescriptionDTO.setPatientName(prescription.getPatientName());
        return prescriptionDTO;
    }

    public List<PrescriptionDTO> getAllPrescription(List<Prescription> prescriptions) {
        List<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();
        for(Prescription prescription : prescriptions) {
            prescriptionDTOs.add(prescriptionTOPrescriptionDTO(prescription));
        }
        return prescriptionDTOs;
    }
}