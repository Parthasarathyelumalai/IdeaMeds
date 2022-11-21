package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
<<<<<<< HEAD
import com.ideas2it.ideameds.model.Medicine;
import com.ideas2it.ideameds.model.Prescription;
import com.ideas2it.ideameds.model.PrescriptionItems;
import com.ideas2it.ideameds.model.User;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
=======
import com.ideas2it.ideameds.model.*;
import com.ideas2it.ideameds.repository.MedicineRepository;
import com.ideas2it.ideameds.repository.PrescriptionRepository;
import com.ideas2it.ideameds.repository.UserRepository;
>>>>>>> nithish_dev
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
<<<<<<< HEAD
=======
    private final CartServiceImpl cartService;
    private final UserRepository userRepository;
>>>>>>> nithish_dev

    @Override
    public Long addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription).getPrescriptionId();
    }

    @Override
<<<<<<< HEAD
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
=======
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
>>>>>>> nithish_dev
    }
}