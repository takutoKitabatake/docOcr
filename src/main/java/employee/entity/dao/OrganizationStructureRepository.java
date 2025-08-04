package employee.entity.dao;

import entity.table.OrganizationStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for OrganizationStructure entity.
 */
@Repository
public interface OrganizationStructureRepository extends JpaRepository<OrganizationStructure, Long> {

    /**
     * Find organization structure by employee ID and year-month.
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYY-MM format
     * @return optional organization structure
     */
    @Query("SELECT os FROM OrganizationStructure os " +
           "JOIN FETCH os.employee e " +
           "JOIN FETCH os.company c " +
           "LEFT JOIN FETCH os.department d " +
           "JOIN FETCH os.position p " +
           "WHERE e.employeeId = :employeeId AND os.yearMonth = :yearMonth")
    Optional<OrganizationStructure> findByEmployeeIdAndYearMonth(
            @Param("employeeId") Long employeeId, 
            @Param("yearMonth") String yearMonth);

    /**
     * Check if organization structure exists for employee ID and year-month.
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYY-MM format
     * @return true if exists, false otherwise
     */
    boolean existsByEmployee_EmployeeIdAndYearMonth(Long employeeId, String yearMonth);
}