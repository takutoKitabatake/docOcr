package common.constants;

/**
 * SQL query constants externalized from DAO interfaces.
 * Queries are defined here to separate SQL logic from Java interfaces.
 */
public final class SqlQueries {

    // ComCheckSiteBaseDao queries
    public static final String COM_CHECK_FIND_ALL_ACTIVE_COMPANIES = 
        "SELECT c FROM Company c WHERE c.createdAt IS NOT NULL ORDER BY c.companyName";
    
    public static final String COM_CHECK_GET_TOTAL_COMPANY_COUNT = 
        "SELECT COUNT(c) FROM Company c";

    // OrganizationStructureRepository queries
    public static final String ORG_STRUCTURE_FIND_BY_EMPLOYEE_AND_YEAR_MONTH = 
        "SELECT os FROM OrganizationStructure os " +
        "JOIN FETCH os.employee e " +
        "JOIN FETCH os.company c " +
        "LEFT JOIN FETCH os.department d " +
        "JOIN FETCH os.position p " +
        "WHERE e.employeeId = :employeeId AND os.yearMonth = :yearMonth";

    // EmployeeReportDownloadRepository queries
    public static final String EMPLOYEE_REPORT_FIND_BY_EMPLOYEE_AND_YEAR_MONTH = 
        "SELECT erd FROM EmployeeReportDownload erd " +
        "JOIN FETCH erd.employee e " +
        "JOIN FETCH erd.report r " +
        "WHERE e.employeeId = :employeeId AND erd.yearMonth = :yearMonth " +
        "ORDER BY erd.downloadedAt DESC";

    private SqlQueries() {
        // Utility class
    }
}