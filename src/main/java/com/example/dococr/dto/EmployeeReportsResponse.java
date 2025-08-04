package com.example.dococr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for employee reports response.
 */
public class EmployeeReportsResponse {

    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String yearMonth;
    private List<ReportDownloadInfo> reports;

    // Constructors
    public EmployeeReportsResponse() {}

    public EmployeeReportsResponse(Long employeeId, String employeeCode, String employeeName, String yearMonth, List<ReportDownloadInfo> reports) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.yearMonth = yearMonth;
        this.reports = reports;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<ReportDownloadInfo> getReports() {
        return reports;
    }

    public void setReports(List<ReportDownloadInfo> reports) {
        this.reports = reports;
    }

    // Nested class for report download information
    public static class ReportDownloadInfo {
        private Long reportId;
        private String reportCode;
        private String reportName;
        
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime downloadedAt;

        public ReportDownloadInfo() {}

        public ReportDownloadInfo(Long reportId, String reportCode, String reportName, LocalDateTime downloadedAt) {
            this.reportId = reportId;
            this.reportCode = reportCode;
            this.reportName = reportName;
            this.downloadedAt = downloadedAt;
        }

        // Getters and Setters
        public Long getReportId() {
            return reportId;
        }

        public void setReportId(Long reportId) {
            this.reportId = reportId;
        }

        public String getReportCode() {
            return reportCode;
        }

        public void setReportCode(String reportCode) {
            this.reportCode = reportCode;
        }

        public String getReportName() {
            return reportName;
        }

        public void setReportName(String reportName) {
            this.reportName = reportName;
        }

        public LocalDateTime getDownloadedAt() {
            return downloadedAt;
        }

        public void setDownloadedAt(LocalDateTime downloadedAt) {
            this.downloadedAt = downloadedAt;
        }
    }
}