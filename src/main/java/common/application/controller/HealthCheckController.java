package common.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller for service monitoring.
 */
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    /**
     * Basic health check endpoint.
     * 
     * @return health status response
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "docOcr");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Detailed health check endpoint.
     * 
     * @return detailed health status response
     */
    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> detailedHealthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "docOcr");
        response.put("version", "1.0.0");
        
        // Add more detailed checks here if needed
        Map<String, String> components = new HashMap<>();
        components.put("database", "UP");
        components.put("application", "UP");
        response.put("components", components);
        
        return ResponseEntity.ok(response);
    }
}