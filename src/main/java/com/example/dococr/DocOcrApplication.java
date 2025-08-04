package com.example.dococr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for Document OCR and Employee Management System.
 */
@SpringBootApplication(scanBasePackages = {
    "common",
    "employee", 
    "tenant.common",
    "entity",
    "com.example.dococr"
})
@EntityScan(basePackages = "entity.table")
@EnableJpaRepositories(basePackages = "employee.entity.dao")
public class DocOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocOcrApplication.class, args);
    }
}