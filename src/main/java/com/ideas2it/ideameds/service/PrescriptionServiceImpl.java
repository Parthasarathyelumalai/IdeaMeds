package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import com.ideas2it.ideameds.repository.UserRepository;
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
    private final CartServiceImpl cartService;
    private final UserRepository userRepository;

    @Override
    public Long addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription).getPrescriptionId();
    }

    @Override
    public Prescription getPrescription(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId).get();
    }

    @Override
    public List<Prescription> getPrescriptionByUser(User user) {
        return prescriptionRepository.getPrescriptionByUser(user);
    }

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

    @Override
    public Long deletePrescriptionById(Long userId, Long prescriptionId) {
        final int DELETED = 1;
        User user = userRepository.findById(userId).get();
        List<Prescription> prescriptions = null;//user.getPrescriptions();
        if(null != prescriptions)
        for(Prescription prescription : prescriptions) {
            if(prescription.getPrescriptionId() == prescriptionId) {
                prescription.setDeletedStatus(DELETED);
                prescriptionRepository.save(prescription);
            }
        }
        return prescriptionId;
    }
}