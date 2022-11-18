package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.dto.PrescriptionDTO;
import com.ideas2it.ideameds.model.PrescriptionItems;

public interface PrescriptionItemsService {
    Long addPrescriptionItems(PrescriptionItems prescriptionItems, PrescriptionDTO prescriptionDTO);
}
