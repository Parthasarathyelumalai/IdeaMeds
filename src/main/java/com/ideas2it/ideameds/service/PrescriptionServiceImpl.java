package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.model.Cart;
import com.ideas2it.ideameds.model.CartItem;
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
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

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Prescription> addPrescription(Prescription prescription) {
        return Optional.of(prescriptionRepository.save(prescription));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Prescription> getPrescription(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Prescription> getPrescriptionByUser(User user) {
        return prescriptionRepository.getPrescriptionByUser(user);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Long deletePrescriptionById(Prescription prescription) {
        final int DELETED = 1;
        prescription.setDeletedStatus(DELETED);
        prescriptionRepository.save(prescription);
        return prescription.getPrescriptionId();
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