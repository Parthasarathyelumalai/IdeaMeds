package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class for  getting token
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-26
 * @version - 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
