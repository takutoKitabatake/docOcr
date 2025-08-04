-- Additional table for tracking employee report downloads
-- This extends the original DDL schema to support the report download API

CREATE TABLE employee_report_download (
    download_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id      BIGINT NOT NULL,
    report_id        BIGINT NOT NULL,
    downloaded_at    TIMESTAMP NOT NULL,
    year_month       CHAR(7) NOT NULL,  -- Format: YYYY-MM for easier querying
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_employee_year_month (employee_id, year_month),
    INDEX idx_downloaded_at (downloaded_at),
    CONSTRAINT fk_download_employee FOREIGN KEY (employee_id)
        REFERENCES employee(employee_id),
    CONSTRAINT fk_download_report FOREIGN KEY (report_id)
        REFERENCES report_master(report_id)
);