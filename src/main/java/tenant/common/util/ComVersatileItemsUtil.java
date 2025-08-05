package tenant.common.util;

import tenant.common.constants.ComConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Versatile utility functions for common operations.
 */
public final class ComVersatileItemsUtil {

    private ComVersatileItemsUtil() {
        // Utility class - prevent instantiation
    }

    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("^\\d{6}$");
    private static final DateTimeFormatter ISO_DATETIME_FORMATTER = 
            DateTimeFormatter.ofPattern(ComConstant.DateFormat.ISO_DATETIME);

    /**
     * Validate year-month format (YYYYMM).
     * 
     * @param yearMonth year-month string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidYearMonth(String yearMonth) {
        if (yearMonth == null || yearMonth.length() != 6) {
            return false;
        }
        
        if (!YEAR_MONTH_PATTERN.matcher(yearMonth).matches()) {
            return false;
        }
        
        try {
            int year = Integer.parseInt(yearMonth.substring(0, 4));
            int month = Integer.parseInt(yearMonth.substring(4, 6));
            
            return year >= 1900 && year <= 2100 && month >= 1 && month <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Convert YYYYMM format to YYYY-MM format.
     * 
     * @param yearMonth year-month in YYYYMM format
     * @return year-month in YYYY-MM format
     * @throws IllegalArgumentException if format is invalid
     */
    public static String formatYearMonth(String yearMonth) {
        if (!isValidYearMonth(yearMonth)) {
            throw new IllegalArgumentException("Invalid year-month format. Expected YYYYMM.");
        }
        return yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6);
    }

    /**
     * Check if a string is null or empty.
     * 
     * @param str string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if a string is not null and not empty.
     * 
     * @param str string to check
     * @return true if not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Get current timestamp as formatted string.
     * 
     * @return current timestamp in ISO format
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(ISO_DATETIME_FORMATTER);
    }

    /**
     * Format LocalDateTime to string.
     * 
     * @param dateTime datetime to format
     * @return formatted datetime string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(ISO_DATETIME_FORMATTER);
    }

    /**
     * Sanitize string for logging (remove potential sensitive information).
     * 
     * @param input input string
     * @return sanitized string
     */
    public static String sanitizeForLogging(String input) {
        if (isEmpty(input)) {
            return input;
        }
        
        // Remove common sensitive patterns
        return input.replaceAll("(?i)(password|token|key|secret)=[^&\\s]*", "$1=***")
                   .replaceAll("(?i)\"(password|token|key|secret)\"\\s*:\\s*\"[^\"]*\"", "\"$1\":\"***\"");
    }

    /**
     * Generate a simple hash code for an object (null-safe).
     * 
     * @param obj object to hash
     * @return hash code
     */
    public static int safeHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }
}