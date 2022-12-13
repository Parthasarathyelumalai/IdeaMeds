/*
 * Copyright 2022 Ideas2IT Technologies. All rights reserved.
 * IDEAS2IT PROPRIETARY/CONFIDENTIAL.
 */
package com.ideas2it.ideameds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Contain main method for the Application
 *
 * @author Parthasarathy Elumalai
 * @version 1.0
 * @since 2022-11-17
 */
@SpringBootApplication
public class IdeaMedsApplication {

    /**
     * Launches the application
     *
     * @param args - Application startup arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(IdeaMedsApplication.class, args);
    }
    
}
