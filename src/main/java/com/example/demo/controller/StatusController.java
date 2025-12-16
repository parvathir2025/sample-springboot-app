package com.example.demo.controller;

import com.example.demo.model.StatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 * Controller for providing service status information.
 * This endpoint is used for monitoring and health checks.
 */
@RestController
public class StatusController {
    
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);
    
    @Value("${spring.application.name:demo}")
    private String applicationName;
    
    @Value("${project.version:unknown}")
    private String projectVersion;

    /**
     * Returns the current status of the service.
     * 
     * @return StatusResponse containing service name, status, and version
     */
    @GetMapping("/status")
    public ResponseEntity<StatusResponse> getStatus() {
        try {
            logger.info("Status endpoint accessed");
            
            // Create a response with dynamic version from properties
            StatusResponse response = new StatusResponse(
                applicationName + "-service",
                "running",
                projectVersion
            );
            
            logger.debug("Returning status: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while getting service status", e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Unable to retrieve service status", 
                e
            );
        }
    }
}
