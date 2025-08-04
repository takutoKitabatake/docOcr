---
applyTo: "**"
description: "�g�D�E���[�Ǘ��̂��߂�DDL��`�iJava + Spring Boot �v���W�F�N�g�p�j"
---

# ? �g�D�n�e�[�u�� DDL��`

## ? ��Ѓ}�X�^ `company`

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

## ? �����}�X�^ `department`

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

## ? ��E�}�X�^ `position`

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

## ? �Ј��}�X�^ `employee`

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

## ? �̐����e�[�u�� `organization_structure`

```sql
CREATE TABLE organization_structure (
    structure_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    year_month       CHAR(7) NOT NULL,  -- ��: 2025-08
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

# ? ���[�n�e�[�u�� DDL��`

## ? ���[�}�X�^ `report_master`

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

## ? ���[���e�[�u�� `report_processing`

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

# ?? ���l
- `ocr_type` ��OCR�����̕��ށi�O���T�[�r�X�^scansnap�^AI�j��\����ł��B
- �̐����e�[�u���ł͌����P�ʂŎЈ��̔z�u�L�^���Ǘ����܂��B
- ���ׂẴe�[�u���� `created_at` / `updated_at` ��W���������Ă��܂��B
