---
applyTo: "**"
description: "組織・帳票管理のためのDDL定義（Java + Spring Boot プロジェクト用）"
---

# ? 組織系テーブル DDL定義

## ? 会社マスタ `company`

```sql
CREATE TABLE company (
    company_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_code     VARCHAR(20) NOT NULL UNIQUE,
    company_name     VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## ? 部署マスタ `department`

```sql
CREATE TABLE department (
    department_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id       BIGINT NOT NULL,
    department_code  VARCHAR(20) NOT NULL,
    department_name  VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(company_id, department_code),
    CONSTRAINT fk_department_company FOREIGN KEY (company_id)
        REFERENCES company(company_id)
);
```

---

## ? 役職マスタ `position`

```sql
CREATE TABLE position (
    position_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    position_code    VARCHAR(20) NOT NULL UNIQUE,
    position_name    VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## ? 社員マスタ `employee`

```sql
CREATE TABLE employee (
    employee_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_id       BIGINT NOT NULL,
    department_id    BIGINT,
    position_id      BIGINT NOT NULL,
    employee_code    VARCHAR(20) NOT NULL UNIQUE,
    employee_name    VARCHAR(100) NOT NULL,
    hire_date        DATE,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_employee_company FOREIGN KEY (company_id)
        REFERENCES company(company_id),
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
        REFERENCES department(department_id),
    CONSTRAINT fk_employee_position FOREIGN KEY (position_id)
        REFERENCES position(position_id)
);
```

---

## ? 体制情報テーブル `organization_structure`

```sql
CREATE TABLE organization_structure (
    structure_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    year_month       CHAR(7) NOT NULL,  -- 例: 2025-08
    employee_id      BIGINT NOT NULL,
    company_id       BIGINT NOT NULL,
    department_id    BIGINT,
    position_id      BIGINT NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(year_month, employee_id),
    CONSTRAINT fk_structure_employee FOREIGN KEY (employee_id)
        REFERENCES employee(employee_id),
    CONSTRAINT fk_structure_company FOREIGN KEY (company_id)
        REFERENCES company(company_id),
    CONSTRAINT fk_structure_department FOREIGN KEY (department_id)
        REFERENCES department(department_id),
    CONSTRAINT fk_structure_position FOREIGN KEY (position_id)
        REFERENCES position(position_id)
);
```

---

# ? 帳票系テーブル DDL定義

## ? 帳票マスタ `report_master`

```sql
CREATE TABLE report_master (
    report_id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_code      VARCHAR(20) NOT NULL UNIQUE,
    report_name      VARCHAR(100) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## ? 帳票情報テーブル `report_processing`

```sql
CREATE TABLE report_processing (
    processing_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    report_id        BIGINT NOT NULL,
    processed_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processor_name   VARCHAR(100) NOT NULL,
    ocr_type         ENUM('external_service', 'scansnap', 'ai') NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_report_processing_report FOREIGN KEY (report_id)
        REFERENCES report_master(report_id)
);
```

---

# ?? 備考
- `ocr_type` はOCR処理の分類（外部サービス／scansnap／AI）を表す列です。
- 体制情報テーブルでは月次単位で社員の配置記録を管理します。
- すべてのテーブルに `created_at` / `updated_at` を標準装備しています。
