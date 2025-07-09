package com.algonquin.aep.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TeachingRequestDTO
 * Validates the functionality of teaching request data transfer object
 */
public class TeachingRequestDTOTest {
    private TeachingRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new TeachingRequestDTO();
    }

    @Test
    @DisplayName("Test professional ID getter and setter")
    public void testSetAndGetProfessionalId() {
        int expectedId = 1;
        requestDTO.setProfessionalId(expectedId);
        assertEquals(expectedId, requestDTO.getProfessionalId(),
                "Professional ID should match the set value");
    }

    @Test
    @DisplayName("Test request status getter and setter")
    public void testSetAndGetStatus() {
        String expectedStatus = "Pending";
        requestDTO.setStatus(expectedStatus);
        assertEquals(expectedStatus, requestDTO.getStatus(),
                "Status should match the set value");
    }
}