package com.enigma.challengespringrestful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.enigma.challengespringrestful")
@EntityScan("com.enigma.challengespringrestful.entity")
@ComponentScan(basePackages = {"com.enigma.challengespringrestful.config","com.enigma.challengespringrestful.constant","com.enigma.challengespringrestful.controller","com.enigma.challengespringrestful.dao","com.enigma.challengespringrestful.dto","com.enigma.challengespringrestful.entity","com.enigma.challengespringrestful.exception","com.enigma.challengespringrestful.repository","com.enigma.challengespringrestful.security","com.enigma.challengespringrestful.service","com.enigma.challengespringrestful.utils"})
public class ChallengeSpringRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeSpringRestfulApplication.class, args);
    }

}
