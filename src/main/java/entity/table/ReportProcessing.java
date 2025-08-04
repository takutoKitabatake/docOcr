package entity.table;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Report Processing entity representing the report_processing table.
 */
@Entity
@Table(name = "report_processing")
public class ReportProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "processing_id")
    private Long processingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private ReportMaster report;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "processor_name", nullable = false, length = 100)
    private String processorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ocr_type", nullable = false)
    private OcrType ocrType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * OCR type enumeration
     */
    public enum OcrType {
        external_service, scansnap, ai
    }

    // Constructors
    public ReportProcessing() {}

    public ReportProcessing(ReportMaster report, String processorName, OcrType ocrType) {
        this.report = report;
        this.processorName = processorName;
        this.ocrType = ocrType;
        this.processedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getProcessingId() {
        return processingId;
    }

    public void setProcessingId(Long processingId) {
        this.processingId = processingId;
    }

    public ReportMaster getReport() {
        return report;
    }

    public void setReport(ReportMaster report) {
        this.report = report;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public OcrType getOcrType() {
        return ocrType;
    }

    public void setOcrType(OcrType ocrType) {
        this.ocrType = ocrType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (processedAt == null) {
            processedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}