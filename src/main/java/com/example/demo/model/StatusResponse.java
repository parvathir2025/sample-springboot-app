package com.example.demo.model;

/**
 * Response object for the status endpoint.
 * This class provides a structured response with type safety.
 */
public class StatusResponse {
    private String service;
    private String status;
    private String version;

    // Default constructor
    public StatusResponse() {
    }

    // Parameterized constructor
    public StatusResponse(String service, String status, String version) {
        this.service = service;
        this.status = status;
        this.version = version;
    }

    // Getters and Setters
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
