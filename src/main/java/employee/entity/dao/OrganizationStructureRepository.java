package employee.entity.dao;

import common.constants.SqlQueries;
import entity.table.OrganizationStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for OrganizationStructure entity.
 * SQL queries are externalized to XML files in resources/sql directory.
 */
@Repository
public interface OrganizationStructureRepository extends JpaRepository<OrganizationStructure, Long> {

    /**
     * Find organization structure by employee ID and year-month.
     * Query defined in: resources/sql/OrganizationStructureRepository.xml
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYY-MM format
     * @return optional organization structure
     */
    @Query(SqlQueries.ORG_STRUCTURE_FIND_BY_EMPLOYEE_AND_YEAR_MONTH)
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