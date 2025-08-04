package com.example.dococr.controller;

import com.example.dococr.dto.EmployeeInfoResponse;
import com.example.dococr.dto.EmployeeReportsResponse;
import com.example.dococr.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for EmployeeController.
 */
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getEmployeeInfo_ValidRequest_ReturnsEmployeeInfo() throws Exception {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        
        EmployeeInfoResponse expectedResponse = new EmployeeInfoResponse(employeeId, "EMP001", "Test Employee", "2024-12");
        expectedResponse.setCompany(new EmployeeInfoResponse.CompanyInfo(1L, "COMP001", "Test Company"));
        expectedResponse.setPosition(new EmployeeInfoResponse.PositionInfo(1L, "POS001", "Test Position"));
        
        when(employeeService.getEmployeeInfo(eq(employeeId), eq(yearMonth)))
                .thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/info", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(employeeId))
                .andExpect(jsonPath("$.employeeCode").value("EMP001"))
                .andExpect(jsonPath("$.employeeName").value("Test Employee"))
                .andExpect(jsonPath("$.yearMonth").value("2024-12"))
                .andExpect(jsonPath("$.company.companyCode").value("COMP001"))
                .andExpect(jsonPath("$.position.positionCode").value("POS001"));
    }

    @Test
    void getEmployeeInfo_EmployeeNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        Long employeeId = 999L;
        String yearMonth = "202412";
        
        when(employeeService.getEmployeeInfo(eq(employeeId), eq(yearMonth)))
                .thenThrow(new EmployeeService.EmployeeNotFoundException("IDが誤っています。"));

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/info", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("IDが誤っています。"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void getEmployeeInfo_NoDataForPeriod_ReturnsNotFound() throws Exception {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        
        when(employeeService.getEmployeeInfo(eq(employeeId), eq(yearMonth)))
                .thenThrow(new EmployeeService.EmployeeDataNotFoundException("その期間にその社員は存在しません。"));

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/info", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("その期間にその社員は存在しません。"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void getEmployeeInfo_InvalidYearMonthFormat_ReturnsBadRequest() throws Exception {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "2024-12"; // Invalid format
        
        when(employeeService.getEmployeeInfo(eq(employeeId), eq(yearMonth)))
                .thenThrow(new IllegalArgumentException("年月は YYYYMM 形式で入力してください。"));

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/info", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("年月は YYYYMM 形式で入力してください。"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void getEmployeeReports_ValidRequest_ReturnsEmployeeReports() throws Exception {
        // Arrange
        Long employeeId = 1L;
        String yearMonth = "202412";
        
        EmployeeReportsResponse expectedResponse = new EmployeeReportsResponse(
                employeeId, "EMP001", "Test Employee", yearMonth, Collections.emptyList());
        
        when(employeeService.getEmployeeReports(eq(employeeId), eq(yearMonth)))
                .thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/reports", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId").value(employeeId))
                .andExpect(jsonPath("$.employeeCode").value("EMP001"))
                .andExpect(jsonPath("$.employeeName").value("Test Employee"))
                .andExpect(jsonPath("$.yearMonth").value(yearMonth))
                .andExpect(jsonPath("$.reports").isArray())
                .andExpect(jsonPath("$.reports").isEmpty());
    }

    @Test
    void getEmployeeReports_EmployeeNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        Long employeeId = 999L;
        String yearMonth = "202412";
        
        when(employeeService.getEmployeeReports(eq(employeeId), eq(yearMonth)))
                .thenThrow(new EmployeeService.EmployeeNotFoundException("IDが誤っています。"));

        // Act & Assert
        mockMvc.perform(get("/employees/{employeeId}/reports", employeeId)
                        .param("yearMonth", yearMonth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("IDが誤っています。"))
                .andExpect(jsonPath("$.status").value(404));
    }
}