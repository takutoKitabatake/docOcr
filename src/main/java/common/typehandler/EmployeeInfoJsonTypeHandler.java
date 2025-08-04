package common.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * JSON TypeHandler for handling employee information.
 * Converts between JSON strings and Map objects for employee data.
 * Note: This is a utility class for JSON handling - MyBatis integration would require adding MyBatis dependency.
 */
public class EmployeeInfoJsonTypeHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convert Map to JSON string.
     * 
     * @param parameter the Map to convert
     * @return JSON string representation
     * @throws RuntimeException if conversion fails
     */
    public static String mapToJson(Map<String, Object> parameter) {
        if (parameter == null) {
            return null;
        }
        
        try {
            return objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Map to JSON string", e);
        }
    }

    /**
     * Convert JSON string to Map.
     * 
     * @param json the JSON string to convert
     * @return Map representation
     * @throws RuntimeException if conversion fails
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON string to Map", e);
        }
    }
}