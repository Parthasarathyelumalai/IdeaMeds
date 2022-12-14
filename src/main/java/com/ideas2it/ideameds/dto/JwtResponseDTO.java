/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for  JWT Response as token
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-26
 * @version - 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
}
