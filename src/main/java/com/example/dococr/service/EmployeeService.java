package com.example.dococr.service;

import com.example.dococr.dto.EmployeeInfoResponse;
import com.example.dococr.dto.EmployeeReportsResponse;
import com.example.dococr.entity.*;
import com.example.dococr.repository.EmployeeRepository;
import com.example.dococr.repository.EmployeeReportDownloadRepository;
import com.example.dococr.repository.OrganizationStructureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for employee-related operations.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationStructureRepository organizationStructureRepository;
    private final EmployeeReportDownloadRepository employeeReportDownloadRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            OrganizationStructureRepository organizationStructureRepository,
            EmployeeReportDownloadRepository employeeReportDownloadRepository) {
        this.employeeRepository = employeeRepository;
        this.organizationStructureRepository = organizationStructureRepository;
        this.employeeReportDownloadRepository = employeeReportDownloadRepository;
    }

    /**
     * Get employee information by employee ID and year-month.
     * 
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYYMM format
     * @return employee information
     * @throws IllegalArgumentException if year-month format is invalid
     * @throws EmployeeNotFoundException if employee ID doesn't exist
     * @throws EmployeeDataNotFoundException if no data exists for the period
     */
    public EmployeeInfoResponse getEmployeeInfo(Long employeeId, String yearMonth) {
        // Validate year-month format first
        String formattedYearMonth = formatYearMonth(yearMonth);
        
        // Check if employee exists
        if (!employeeRepository.existsByEmployeeId(employeeId)) {
            throw new EmployeeNotFoundException("IDが誤っています。");
        }

        // Find organization structure for the given period
        Optional<OrganizationStructure> orgStructure = organizationStructureRepository
                .findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth);

        if (orgStructure.isEmpty()) {
            throw new EmployeeDataNotFoundException("その期間にその社員は存在しません。");
        }

        return buildEmployeeInfoResponse(orgStructure.get());
    }

    /**
     * Get employee's downloaded reports by employee ID and year-month.
     * 
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYYMM format
     * @return employee's downloaded reports
     * @throws IllegalArgumentException if year-month format is invalid
     * @throws EmployeeNotFoundException if employee ID doesn't exist
     * @throws EmployeeDataNotFoundException if no data exists for the period
     */
    public EmployeeReportsResponse getEmployeeReports(Long employeeId, String yearMonth) {
        // Validate year-month format first
        String formattedYearMonth = formatYearMonth(yearMonth);
        
        // Check if employee exists
        if (!employeeRepository.existsByEmployeeId(employeeId)) {
            throw new EmployeeNotFoundException("IDが誤っています。");
        }

        // Check if employee existed in that period
        if (!organizationStructureRepository.existsByEmployee_EmployeeIdAndYearMonth(employeeId, formattedYearMonth)) {
            throw new EmployeeDataNotFoundException("その期間にその社員は存在しません。");
        }

        // Get employee info for response
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();

        // Find downloaded reports for the given period
        List<EmployeeReportDownload> downloads = employeeReportDownloadRepository
                .findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth);

        return buildEmployeeReportsResponse(employee, yearMonth, downloads);
    }

    /**
     * Convert YYYYMM format to YYYY-MM format.
     * 
     * @param yearMonth year-month in YYYYMM format
     * @return year-month in YYYY-MM format
     */
    private String formatYearMonth(String yearMonth) {
        if (yearMonth == null || yearMonth.length() != 6) {
            throw new IllegalArgumentException("年月は YYYYMM 形式で入力してください。");
        }
        return yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6);
    }

    /**
     * Build employee info response from organization structure.
     */
    private EmployeeInfoResponse buildEmployeeInfoResponse(OrganizationStructure orgStructure) {
        Employee employee = orgStructure.getEmployee();
        Company company = orgStructure.getCompany();
        Department department = orgStructure.getDepartment();
        Position position = orgStructure.getPosition();

        EmployeeInfoResponse response = new EmployeeInfoResponse(
                employee.getEmployeeId(),
                employee.getEmployeeCode(),
                employee.getEmployeeName(),
                orgStructure.getYearMonth()
        );

        // Set company info
        response.setCompany(new EmployeeInfoResponse.CompanyInfo(
                company.getCompanyId(),
                company.getCompanyCode(),
                company.getCompanyName()
        ));

        // Set department info (nullable)
        if (department != null) {
            response.setDepartment(new EmployeeInfoResponse.DepartmentInfo(
                    department.getDepartmentId(),
                    department.getDepartmentCode(),
                    department.getDepartmentName()
            ));
        }

        // Set position info
        response.setPosition(new EmployeeInfoResponse.PositionInfo(
                position.getPositionId(),
                position.getPositionCode(),
                position.getPositionName()
        ));

        return response;
    }

    /**
     * Build employee reports response from downloads.
     */
    private EmployeeReportsResponse buildEmployeeReportsResponse(
            Employee employee, 
            String yearMonth, 
            List<EmployeeReportDownload> downloads) {
        
        List<EmployeeReportsResponse.ReportDownloadInfo> reportInfos = downloads.stream()
                .map(download -> new EmployeeReportsResponse.ReportDownloadInfo(
                        download.getReport().getReportId(),
                        download.getReport().getReportCode(),
                        download.getReport().getReportName(),
                        download.getDownloadedAt()
                ))
                .collect(Collectors.toList());

        return new EmployeeReportsResponse(
                employee.getEmployeeId(),
                employee.getEmployeeCode(),
                employee.getEmployeeName(),
                yearMonth,
                reportInfos
        );
    }

    // Exception classes
    public static class EmployeeNotFoundException extends RuntimeException {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }

    public static class EmployeeDataNotFoundException extends RuntimeException {
        public EmployeeDataNotFoundException(String message) {
            super(message);
        }
    }
}