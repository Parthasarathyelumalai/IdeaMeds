package com.ideas2it.ideameds.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseResponseDTO {
    private Long warehouseId;

    private String warehouseName;

    private String location;

    private List<BrandItemsDTO> brandItemsDTOList;
}
