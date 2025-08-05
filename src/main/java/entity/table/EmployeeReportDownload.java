package entity.table;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Employee Report Download entity for tracking report downloads by employees.
 * This is an additional table not in the original DDL but needed for the second API.
 */
@Entity
@Table(name = "employee_report_download")
public class EmployeeReportDownload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "download_id")
    private Long downloadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private ReportMaster report;

    @Column(name = "downloaded_at", nullable = false)
    private LocalDateTime downloadedAt;

    @Column(name = "year_month", nullable = false, length = 7)
    private String yearMonth; // Format: YYYY-MM for easier querying

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public EmployeeReportDownload() {}

    public EmployeeReportDownload(Employee employee, ReportMaster report, LocalDateTime downloadedAt, String yearMonth) {
        this.employee = employee;
        this.report = report;
        this.downloadedAt = downloadedAt;
        this.yearMonth = yearMonth;
    }

    // Getters and Setters
    public Long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(Long downloadId) {
        this.downloadId = downloadId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ReportMaster getReport() {
        return report;
    }

    public void setReport(ReportMaster report) {
        this.report = report;
    }

    public LocalDateTime getDownloadedAt() {
        return downloadedAt;
    }

    public void setDownloadedAt(LocalDateTime downloadedAt) {
        this.downloadedAt = downloadedAt;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}