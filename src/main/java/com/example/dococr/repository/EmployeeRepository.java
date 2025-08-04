package com.example.dococr.repository;

import com.example.dococr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Employee entity.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by employee code.
     * @param employeeCode the employee code
     * @return optional employee
     */
    Optional<Employee> findByEmployeeCode(String employeeCode);

    /**
     * Check if employee exists by employee ID.
     * @param employeeId the employee ID
     * @return true if exists, false otherwise
     */
    boolean existsByEmployeeId(Long employeeId);
}