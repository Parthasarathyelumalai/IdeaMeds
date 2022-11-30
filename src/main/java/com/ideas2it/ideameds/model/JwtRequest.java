package com.ideas2it.ideameds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Class for getting username and password as jwt request
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-26
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    @NotBlank(message = "Username should be mentioned eg.Your email Id")
    @Email(regexp = "^[a-z]{1}[a-z0-9._]+@[a-z0-9]+[.][a-z]*$", message = "Invalid format e.g. john@xyz.com")
    private String userName;
    @NotBlank(message = "password should be mentioned eg.**********")
    private String password;
}
