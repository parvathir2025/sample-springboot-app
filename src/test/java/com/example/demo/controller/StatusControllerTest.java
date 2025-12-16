package com.example.demo.controller;

import com.example.demo.model.StatusResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the StatusController class.
 */
@WebMvcTest(StatusController.class)
@TestPropertySource(properties = {
    "spring.application.name=demo",
    "project.version=0.0.1-TEST"
})
public class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatusController statusController;

    /**
     * Test case 1: Verify that the /status endpoint returns the correct JSON structure and values.
     */
    @Test
    public void testGetStatusReturnsCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/status")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service", is("demo-service")))
                .andExpect(jsonPath("$.status", is("running")))
                .andExpect(jsonPath("$.version", is("0.0.1-TEST")));
    }

    /**
     * Test case 2: Simulate a failure in the service and ensure that the endpoint returns a proper error response.
     * 
     * This test uses a subclass of StatusController to force an exception to be thrown.
     */
    @Test
    public void testGetStatusHandlesErrorsGracefully() throws Exception {
        // Create a test subclass of StatusController that throws an exception
        StatusController errorController = new StatusController() {
            @Override
            public org.springframework.http.ResponseEntity<StatusResponse> getStatus() {
                throw new RuntimeException("Simulated service failure");
            }
        };

        // Use reflection to set the errorController as the controller to be tested
        java.lang.reflect.Field field = StatusControllerTest.class.getDeclaredField("statusController");
        field.setAccessible(true);
        field.set(this, errorController);

        // Perform the test
        mockMvc.perform(MockMvcRequestBuilders
                .get("/status")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}
