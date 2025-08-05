package employee.entity.dao;

import common.constants.SqlQueries;
import entity.table.EmployeeReportDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for EmployeeReportDownload entity.
 * SQL queries are externalized to XML files in resources/sql directory.
 */
@Repository
public interface EmployeeReportDownloadRepository extends JpaRepository<EmployeeReportDownload, Long> {

    /**
     * Find all report downloads by employee ID and year-month.
     * Query defined in: resources/sql/EmployeeReportDownloadRepository.xml
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYY-MM format
     * @return list of employee report downloads
     */
    @Query(SqlQueries.EMPLOYEE_REPORT_FIND_BY_EMPLOYEE_AND_YEAR_MONTH)
    List<EmployeeReportDownload> findByEmployeeIdAndYearMonth(
            @Param("employeeId") Long employeeId, 
            @Param("yearMonth") String yearMonth);
}