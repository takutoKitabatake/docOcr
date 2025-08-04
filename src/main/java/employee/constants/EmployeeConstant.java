package employee.constants;

/**
 * Constants for employee functionality.
 */
public final class EmployeeConstant {

    private EmployeeConstant() {
        // Utility class - prevent instantiation
    }

    /**
     * Year-month format constants
     */
    public static final class YearMonth {
        public static final String INPUT_FORMAT_PATTERN = "YYYYMM";
        public static final String STORAGE_FORMAT_PATTERN = "YYYY-MM";
        public static final int INPUT_LENGTH = 6;
        
        private YearMonth() {}
    }

    /**
     * Error message constants
     */
    public static final class ErrorMessages {
        public static final String INVALID_YEAR_MONTH_FORMAT = "年月は YYYYMM 形式で入力してください。";
        public static final String EMPLOYEE_NOT_FOUND = "IDが誤っています。";
        public static final String EMPLOYEE_DATA_NOT_FOUND = "その期間にその社員は存在しません。";
        
        private ErrorMessages() {}
    }

    /**
     * API endpoint constants
     */
    public static final class Endpoints {
        public static final String EMPLOYEES_BASE = "/employees";
        public static final String EMPLOYEE_INFO = "/{employeeId}/info";
        public static final String EMPLOYEE_REPORTS = "/{employeeId}/reports";
        
        private Endpoints() {}
    }
}