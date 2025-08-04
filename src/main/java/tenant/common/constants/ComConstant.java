package tenant.common.constants;

/**
 * Common constants for tenant operations.
 */
public final class ComConstant {

    private ComConstant() {
        // Utility class - prevent instantiation
    }

    /**
     * Application information constants
     */
    public static final class Application {
        public static final String NAME = "docOcr";
        public static final String VERSION = "1.0.0";
        public static final String DESCRIPTION = "Document OCR and Employee Management System";
        
        private Application() {}
    }

    /**
     * Date format constants
     */
    public static final class DateFormat {
        public static final String ISO_DATE = "yyyy-MM-dd";
        public static final String ISO_DATETIME = "yyyy-MM-dd HH:mm:ss";
        public static final String YEAR_MONTH = "yyyy-MM";
        public static final String COMPACT_YEAR_MONTH = "yyyyMM";
        
        private DateFormat() {}
    }

    /**
     * Response status constants
     */
    public static final class Status {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";
        public static final String WARNING = "WARNING";
        public static final String UP = "UP";
        public static final String DOWN = "DOWN";
        
        private Status() {}
    }

    /**
     * HTTP status codes
     */
    public static final class HttpStatus {
        public static final int OK = 200;
        public static final int BAD_REQUEST = 400;
        public static final int NOT_FOUND = 404;
        public static final int INTERNAL_SERVER_ERROR = 500;
        
        private HttpStatus() {}
    }

    /**
     * Logging related constants
     */
    public static final class Logging {
        public static final String REQUEST_START = "Request started: ";
        public static final String REQUEST_END = "Request completed: ";
        public static final String ERROR_OCCURRED = "Error occurred: ";
        
        private Logging() {}
    }
}