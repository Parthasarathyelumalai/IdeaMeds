/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds;

import com.ideas2it.ideameds.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Contain main method for the Application
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-17
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
public class MiniProjectApplication {

    /**
     * Launches the application
     *
     * @param args - Application startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MiniProjectApplication.class, args);
    }

}
