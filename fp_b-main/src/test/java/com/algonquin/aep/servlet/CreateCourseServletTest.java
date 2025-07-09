package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class CreateCourseServletTest {

    @Test
    void doPost() {
        CourseDAO courseDAO = new CourseDAOImpl();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("courseTitleTest");
        courseDTO.setCode("courseCode");
        courseDTO.setTerm("term");
        courseDTO.setOutline("courseOutline");
        courseDTO.setSchedule("schedule");
        courseDTO.setPreferredQualifications("qualifications");
        courseDTO.setDeliveryMethod("Hybrid");
        courseDTO.setCompensation(1.23);
        courseDTO.setInstitutionId(1);
        courseDAO.insertCourse(courseDTO);
        List<CourseDTO> courses = courseDAO.getCoursesByInstitutionId(1);
        CourseDTO result = courses.stream().filter(course -> "courseTitleTest".equals(course.getTitle())).toList().stream().findFirst().get();
        Assertions.assertEquals("courseCode", result.getCode());
    }
}