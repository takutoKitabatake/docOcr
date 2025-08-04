package com.example.dococr.repository;

import com.example.dococr.entity.EmployeeReportDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for EmployeeReportDownload entity.
 */
@Repository
public interface EmployeeReportDownloadRepository extends JpaRepository<EmployeeReportDownload, Long> {

    /**
     * Find all report downloads by employee ID and year-month.
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYY-MM format
     * @return list of employee report downloads
     */
    @Query("SELECT erd FROM EmployeeReportDownload erd " +
           "JOIN FETCH erd.employee e " +
           "JOIN FETCH erd.report r " +
           "WHERE e.employeeId = :employeeId AND erd.yearMonth = :yearMonth " +
           "ORDER BY erd.downloadedAt DESC")
    List<EmployeeReportDownload> findByEmployeeIdAndYearMonth(
            @Param("employeeId") Long employeeId, 
            @Param("yearMonth") String yearMonth);
}