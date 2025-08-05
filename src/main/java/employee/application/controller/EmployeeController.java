package employee.application.controller;

import employee.application.resource.EmployeeInfoResponse;
import employee.application.resource.EmployeeReportsResponse;
import employee.domain.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for employee-related endpoints.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get employee information by employee ID and year-month.
     * 
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYYMM format
     * @return employee information
     */
    @GetMapping("/{employeeId}/info")
    public ResponseEntity<EmployeeInfoResponse> getEmployeeInfo(
            @PathVariable Long employeeId,
            @RequestParam String yearMonth) {
        
        EmployeeInfoResponse response = employeeService.getEmployeeInfo(employeeId, yearMonth);
        return ResponseEntity.ok(response);
    }

    /**
     * Get employee's downloaded reports by employee ID and year-month.
     * 
     * @param employeeId the employee ID
     * @param yearMonth the year-month in YYYYMM format
     * @return employee's downloaded reports
     */
    @GetMapping("/{employeeId}/reports")
    public ResponseEntity<EmployeeReportsResponse> getEmployeeReports(
            @PathVariable Long employeeId,
            @RequestParam String yearMonth) {
        
        EmployeeReportsResponse response = employeeService.getEmployeeReports(employeeId, yearMonth);
        return ResponseEntity.ok(response);
    }
}