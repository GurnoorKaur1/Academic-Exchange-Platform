package com.algonquin.aep.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AcademicProfessionalDTO
 * Validates the functionality of academic professional data transfer object
 */
public class AcademicProfessionalDTOTest {
    private AcademicProfessionalDTO professionalDTO;

    @BeforeEach
    void setUp() {
        professionalDTO = new AcademicProfessionalDTO();
    }

    @Test
    @DisplayName("Test academic position getter and setter")
    public void testSetAndGetAcademicPosition() {
        String expectedPosition = "Professor";
        professionalDTO.setAcademicPosition(expectedPosition);
        assertEquals(expectedPosition, professionalDTO.getAcademicPosition(),
                "Academic position should match the set value");
    }

    @Test
    @DisplayName("Test area of expertise getter and setter")
    public void testSetAndGetAreaOfExpertise() {
        String expectedExpertise = "Computer Science";
        professionalDTO.setAreaOfExpertise(expectedExpertise);
        assertEquals(expectedExpertise, professionalDTO.getAreaOfExpertise(),
                "Area of expertise should match the set value");
    }
}