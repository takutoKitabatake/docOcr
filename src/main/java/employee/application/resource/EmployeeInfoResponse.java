package employee.application.resource;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO for employee information response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeInfoResponse {

    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String yearMonth;
    private CompanyInfo company;
    private DepartmentInfo department;
    private PositionInfo position;

    // Constructors
    public EmployeeInfoResponse() {}

    public EmployeeInfoResponse(Long employeeId, String employeeCode, String employeeName, String yearMonth) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.yearMonth = yearMonth;
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

    public CompanyInfo getCompany() {
        return company;
    }

    public void setCompany(CompanyInfo company) {
        this.company = company;
    }

    public DepartmentInfo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentInfo department) {
        this.department = department;
    }

    public PositionInfo getPosition() {
        return position;
    }

    public void setPosition(PositionInfo position) {
        this.position = position;
    }

    // Nested classes for company, department, and position info
    public static class CompanyInfo {
        private Long companyId;
        private String companyCode;
        private String companyName;

        public CompanyInfo() {}

        public CompanyInfo(Long companyId, String companyCode, String companyName) {
            this.companyId = companyId;
            this.companyCode = companyCode;
            this.companyName = companyName;
        }

        // Getters and Setters
        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }

    public static class DepartmentInfo {
        private Long departmentId;
        private String departmentCode;
        private String departmentName;

        public DepartmentInfo() {}

        public DepartmentInfo(Long departmentId, String departmentCode, String departmentName) {
            this.departmentId = departmentId;
            this.departmentCode = departmentCode;
            this.departmentName = departmentName;
        }

        // Getters and Setters
        public Long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(String departmentCode) {
            this.departmentCode = departmentCode;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }

    public static class PositionInfo {
        private Long positionId;
        private String positionCode;
        private String positionName;

        public PositionInfo() {}

        public PositionInfo(Long positionId, String positionCode, String positionName) {
            this.positionId = positionId;
            this.positionCode = positionCode;
            this.positionName = positionName;
        }

        // Getters and Setters
        public Long getPositionId() {
            return positionId;
        }

        public void setPositionId(Long positionId) {
            this.positionId = positionId;
        }

        public String getPositionCode() {
            return positionCode;
        }

        public void setPositionCode(String positionCode) {
            this.positionCode = positionCode;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }
    }
}