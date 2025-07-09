package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetCourseDetailServletTest {

    @Test
    void doGet() {
        CourseDAO courseDAO = new CourseDAOImpl();
        CourseDTO course = courseDAO.findCourseById(19);
        Assertions.assertEquals(course.getTitle(), "courseTitleTest");
    }
}