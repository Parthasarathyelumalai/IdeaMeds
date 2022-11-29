package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Class for getting username & password
 *
 * @author - Parthasarathy Elumalai
 * @since - 2022-11-26
 * @version - 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @NotBlank(message = "Username should be mentioned eg.Your email Id")
    @Email(regexp = "^[a-z]{1}[a-z0-9._]+@[a-z0-9]+[.][a-z]*$",message = "Invalid format e.g. john@xyz.com")
    private String username;
    @NotBlank(message = "password should be mentioned eg.**********")
    private String password;
}
