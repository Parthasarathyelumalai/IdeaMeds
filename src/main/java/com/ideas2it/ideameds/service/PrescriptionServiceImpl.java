/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.exception.PrescriptionExpiredException;
import com.ideas2it.ideameds.exception.PrescriptionNotFoundException;
import com.ideas2it.ideameds.exception.UserException;
import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.util.DateTimeValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface Implementation
 * Performs Create, Read, Update and Delete operations for the Prescription
 * @author Nithish K
 * @version 1.0
 * @since 2022-11-18
 */
@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService{
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final CartServiceImpl cartService;
    private final UserRepository userRepository;
    private final DateTimeValidation dateTimeValidation;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *{@inheritDoc}
     */
    @Override
    public PrescriptionDTO addPrescription(PrescriptionDTO prescriptionDTO, Long userId) throws PrescriptionExpiredException, UserException {
        Prescription prescription = modelMapper.map(prescriptionDTO, Prescription.class);
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            prescription.setUser(user.get());
            dateTimeValidation.validateDateOfIssue(prescriptionDTO.getDateOfIssue());
            return modelMapper.map(prescriptionRepository.save(prescription), PrescriptionDTO.class);
        } else throw new UserException("User Not Found");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrescriptionDTO getPrescription(Long prescriptionId) throws PrescriptionNotFoundException {
        Optional<Prescription> prescription = prescriptionRepository.findById(prescriptionId);
        if (prescription.isPresent()) return modelMapper.map(prescription,PrescriptionDTO.class);
        else throw new PrescriptionNotFoundException("Prescription Not Found");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<PrescriptionDTO> getPrescriptionByUser(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return prescriptionRepository.getPrescriptionByUser(user.get()).stream()
                        .map(prescription -> modelMapper
                        .map(prescription, PrescriptionDTO.class))
                        .toList();
        else throw new UserException("User Not Found");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long deletePrescriptionById(Long prescriptionId, Long userId) throws PrescriptionNotFoundException, UserException {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            List<Prescription> prescriptions = user.get().getPrescription();

            if (prescriptions.isEmpty()) throw new PrescriptionNotFoundException("Prescription Not Found");
            else {
                for (Prescription prescription : prescriptions) {
                    if (prescription.getPrescriptionId().equals(prescriptionId)) {
                        prescription.setDeletedStatus(true);
                        return prescriptionRepository.save(prescription).getPrescriptionId();
                    }
                }
            }
        } else throw new UserException("User Not Found");
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void addToCart(List<PrescriptionItems> prescriptionItems, User user) {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        if(prescriptionItems != null){
            List<Medicine> medicines = medicineRepository.findAll();
            for(PrescriptionItems prescriptionItem : prescriptionItems) {
                for (Medicine medicine : medicines) {
                    if (medicine.getMedicineName().equals(prescriptionItem.getMedicineName())) {
                        CartItem cartItem = new CartItem();
                        cartItem.setMedicine(medicine);
                        cartItem.setQuantity(prescriptionItem.getQuantity());
                        cartItems.add(cartItem);
                        cart.setCartItemList(cartItems);
                    }
                }
            }
        }
        cartService.addCart(user.getUserId(), cart);
    }
}