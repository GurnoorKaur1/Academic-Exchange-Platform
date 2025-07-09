package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateCourseServletTest {

    @Test
    void doPost() {
        CourseDAO courseDAO = new CourseDAOImpl();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(19);
        courseDTO.setTitle("courseTitleUpdate");
        courseDTO.setCode("courseCode");
        courseDTO.setTerm("term");
        courseDTO.setOutline("courseOutline");
        courseDTO.setSchedule("schedule");
        courseDTO.setPreferredQualifications("qualifications");
        courseDTO.setDeliveryMethod("Hybrid");
        courseDTO.setCompensation(1.23);
        courseDAO.updateCourse(courseDTO);

        CourseDTO course = courseDAO.findCourseById(19);
        assertEquals("courseTitleUpdate", course.getTitle());
    }
}