package employee.domain.service;

import employee.application.resource.EmployeeInfoResponse;
import employee.application.resource.EmployeeReportsResponse;
import entity.table.*;
import employee.entity.dao.EmployeeRepository;
import employee.entity.dao.EmployeeReportDownloadRepository;
import employee.entity.dao.OrganizationStructureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for EmployeeService.
 */
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private OrganizationStructureRepository organizationStructureRepository;

    @Mock
    private EmployeeReportDownloadRepository employeeReportDownloadRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private Company testCompany;
    private Position testPosition;
    private Department testDepartment;
    private OrganizationStructure testOrgStructure;
    private ReportMaster testReport;
    private EmployeeReportDownload testDownload;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCompany = new Company("COMP001", "Test Company");
        testCompany.setCompanyId(1L);

        testPosition = new Position("POS001", "Test Position");
        testPosition.setPositionId(1L);

        testDepartment = new Department(testCompany, "DEPT001", "Test Department");
        testDepartment.setDepartmentId(1L);

        testEmployee = new Employee(testCompany, testPosition, "EMP001", "Test Employee");
        testEmployee.setEmployeeId(1L);

        testOrgStructure = new OrganizationStructure("2024-12", testEmployee, testCompany, testPosition);
        testOrgStructure.setDepartment(testDepartment);

        testReport = new ReportMaster("REP001", "Test Report");
        testReport.setReportId(1L);

        testDownload = new EmployeeReportDownload(testEmployee, testReport, LocalDateTime.now(), "2024-12");
    }

    @Test
    void getEmployeeInfo_ValidEmployeeAndPeriod_ReturnsEmployeeInfo() {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        String formattedYearMonth = "2024-12";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(true);
        when(organizationStructureRepository.findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(Optional.of(testOrgStructure));

        // Act
        EmployeeInfoResponse result = employeeService.getEmployeeInfo(employeeId, yearMonth);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("EMP001", result.getEmployeeCode());
        assertEquals("Test Employee", result.getEmployeeName());
        assertEquals("2024-12", result.getYearMonth());
        assertNotNull(result.getCompany());
        assertNotNull(result.getDepartment());
        assertNotNull(result.getPosition());
    }

    @Test
    void getEmployeeInfo_InvalidEmployeeId_ThrowsEmployeeNotFoundException() {
        // Arrange
        Long employeeId = 999L;
        String yearMonth = "202412";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(false);

        // Act & Assert
        EmployeeService.EmployeeNotFoundException exception = assertThrows(
                EmployeeService.EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeInfo(employeeId, yearMonth)
        );
        assertEquals("IDが誤っています。", exception.getMessage());
    }

    @Test
    void getEmployeeInfo_NoDataForPeriod_ThrowsEmployeeDataNotFoundException() {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        String formattedYearMonth = "2024-12";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(true);
        when(organizationStructureRepository.findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(Optional.empty());

        // Act & Assert
        EmployeeService.EmployeeDataNotFoundException exception = assertThrows(
                EmployeeService.EmployeeDataNotFoundException.class,
                () -> employeeService.getEmployeeInfo(employeeId, yearMonth)
        );
        assertEquals("その期間にその社員は存在しません。", exception.getMessage());
    }

    @Test
    void getEmployeeReports_ValidEmployeeAndPeriod_ReturnsEmployeeReports() {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        String formattedYearMonth = "2024-12";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(true);
        when(organizationStructureRepository.existsByEmployee_EmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(testEmployee));
        when(employeeReportDownloadRepository.findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(List.of(testDownload));

        // Act
        EmployeeReportsResponse result = employeeService.getEmployeeReports(employeeId, yearMonth);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("EMP001", result.getEmployeeCode());
        assertEquals("Test Employee", result.getEmployeeName());
        assertEquals(yearMonth, result.getYearMonth());
        assertEquals(1, result.getReports().size());
    }

    @Test
    void getEmployeeReports_InvalidEmployeeId_ThrowsEmployeeNotFoundException() {
        // Arrange
        Long employeeId = 999L;
        String yearMonth = "202412";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(false);

        // Act & Assert
        EmployeeService.EmployeeNotFoundException exception = assertThrows(
                EmployeeService.EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeReports(employeeId, yearMonth)
        );
        assertEquals("IDが誤っています。", exception.getMessage());
    }

    @Test
    void getEmployeeReports_NoDataForPeriod_ThrowsEmployeeDataNotFoundException() {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        String formattedYearMonth = "2024-12";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(true);
        when(organizationStructureRepository.existsByEmployee_EmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(false);

        // Act & Assert
        EmployeeService.EmployeeDataNotFoundException exception = assertThrows(
                EmployeeService.EmployeeDataNotFoundException.class,
                () -> employeeService.getEmployeeReports(employeeId, yearMonth)
        );
        assertEquals("その期間にその社員は存在しません。", exception.getMessage());
    }

    @Test
    void getEmployeeReports_ValidEmployeeButNoDownloads_ReturnsEmptyList() {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        String formattedYearMonth = "2024-12";

        when(employeeRepository.existsByEmployeeId(employeeId)).thenReturn(true);
        when(organizationStructureRepository.existsByEmployee_EmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(true);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(testEmployee));
        when(employeeReportDownloadRepository.findByEmployeeIdAndYearMonth(employeeId, formattedYearMonth))
                .thenReturn(Collections.emptyList());

        // Act
        EmployeeReportsResponse result = employeeService.getEmployeeReports(employeeId, yearMonth);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getEmployeeId());
        assertTrue(result.getReports().isEmpty());
    }

    @Test
    void formatYearMonth_InvalidFormat_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
                employeeService.getEmployeeInfo(1L, "2024-12")); // Invalid format
        assertThrows(IllegalArgumentException.class, () -> 
                employeeService.getEmployeeInfo(1L, "24")); // Too short
        assertThrows(IllegalArgumentException.class, () -> 
                employeeService.getEmployeeInfo(1L, "")); // Empty
        assertThrows(IllegalArgumentException.class, () -> 
                employeeService.getEmployeeInfo(1L, null)); // Null
    }
}