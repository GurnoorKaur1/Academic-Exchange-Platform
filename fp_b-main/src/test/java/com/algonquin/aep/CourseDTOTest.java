package com.algonquin.aep.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CourseDTO
 * Validates the functionality of course data transfer object
 */
public class CourseDTOTest {
    private CourseDTO courseDTO;

    @BeforeEach
    void setUp() {
        courseDTO = new CourseDTO();
    }

    @Test
    @DisplayName("Test course code getter and setter")
    public void testSetAndGetCourseCode() {
        String expectedCode = "CST8288";
        courseDTO.setCode(expectedCode);
        assertEquals(expectedCode, courseDTO.getCode(), "Course code should match the set value");
    }

    @Test
    @DisplayName("Test course title getter and setter")
    public void testSetAndGetTitle() {
        String expectedTitle = "Java Programming";
        courseDTO.setTitle(expectedTitle);
        assertEquals(expectedTitle, courseDTO.getTitle(), "Course title should match the set value");
    }
}