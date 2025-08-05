package tenant.common.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration resource for external API settings.
 */
@Component
@ConfigurationProperties(prefix = "com.external.api")
public class ComExternalApiConfigResource {

    private String baseUrl;
    private String apiKey;
    private int timeoutSeconds = 30;
    private boolean enabled = true;

    // OCR Service configuration
    private OcrConfig ocr = new OcrConfig();

    // Report Service configuration  
    private ReportConfig report = new ReportConfig();

    // Getters and Setters
    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public OcrConfig getOcr() {
        return ocr;
    }

    public void setOcr(OcrConfig ocr) {
        this.ocr = ocr;
    }

    public ReportConfig getReport() {
        return report;
    }

    public void setReport(ReportConfig report) {
        this.report = report;
    }

    public static class OcrConfig {
        private String endpoint = "/ocr/process";
        private String[] supportedFormats = {"pdf", "jpg", "png"};
        private int maxFileSizeMb = 10;

        // Getters and Setters
        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String[] getSupportedFormats() {
            return supportedFormats;
        }

        public void setSupportedFormats(String[] supportedFormats) {
            this.supportedFormats = supportedFormats;
        }

        public int getMaxFileSizeMb() {
            return maxFileSizeMb;
        }

        public void setMaxFileSizeMb(int maxFileSizeMb) {
            this.maxFileSizeMb = maxFileSizeMb;
        }
    }

    public static class ReportConfig {
        private String endpoint = "/reports/generate";
        private String[] outputFormats = {"pdf", "excel"};

        // Getters and Setters
        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String[] getOutputFormats() {
            return outputFormats;
        }

        public void setOutputFormats(String[] outputFormats) {
            this.outputFormats = outputFormats;
        }
    }
}