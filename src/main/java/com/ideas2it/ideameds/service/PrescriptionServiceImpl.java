/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.CustomException;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.Constants;
import com.ideas2it.ideameds.util.DateTimeValidation;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for the Prescription
 *
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Constructs a new object
     *
     * @param prescriptionRepository create new instance for prescription repository
     * @param userRepository         create new instance for user repository
     */
    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Prescription prescription = modelMapper.map(prescriptionDTO, Prescription.class);
            prescription.setPrescriptionItems(prescriptionDTO.getPrescriptionItems().stream().map(prescriptionItemsDTO -> modelMapper.map(prescriptionItemsDTO, PrescriptionItems.class)).toList());
            prescription.setUser(user.get());
            DateTimeValidation.validateDateOfIssue(prescriptionDTO.getDateOfIssue());
            prescription.setCreatedAt(DateTimeValidation.getDate());
            prescription.setModifiedAt(DateTimeValidation.getDate());
            return modelMapper.map(prescriptionRepository.save(prescription), PrescriptionDTO.class);
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionDTO getPrescriptionByPrescriptionId(Long prescriptionId) throws CustomException {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) return modelMapper.map(prescription, PrescriptionDTO.class);
        else throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PrescriptionDTO> getPrescriptionByUser(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<List<Prescription>> prescriptionList = prescriptionRepository.findByUser(user.get());
            if (prescriptionList.isPresent())
                return prescriptionList.get().stream()
                        .map(prescription -> modelMapper.map(prescription, PrescriptionDTO.class))
                        .toList();
        }else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long deletePrescriptionById(Long prescriptionId, Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Prescription> prescriptions = user.get().getPrescription();

            if (prescriptions.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, Constants.PRESCRIPTION_NOT_FOUND);
            else {
                for (Prescription prescription : prescriptions) {
                    if (prescription.getPrescriptionId().equals(prescriptionId)) {
                        prescription.setDeletedStatus(true);
                        return prescriptionRepository.save(prescription).getPrescriptionId();
                    }
                }
            }
        } else throw new CustomException(HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND);
        return null;
    }
}