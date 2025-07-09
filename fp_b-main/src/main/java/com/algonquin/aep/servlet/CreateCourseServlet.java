package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The servlet handle the request to create a course.
 */
@WebServlet("/createCourse")
public class CreateCourseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CreateCourseServlet.class);
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        courseDAO = new CourseDAOImpl();
    }

    /**
     * Get data from http request and set values to the dto and then insert it to the table courses.
     *
     * @param request  http request
     * @param response http response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int institutionId = (int) session.getAttribute("userId");

        logger.info("Create a course detail for institution ID: {}", institutionId);

        try {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setTitle(request.getParameter("courseTitle"));
            courseDTO.setCode(request.getParameter("courseCode"));
            courseDTO.setTerm(request.getParameter("term"));
            courseDTO.setOutline(request.getParameter("courseOutline"));
            courseDTO.setSchedule(request.getParameter("schedule"));
            courseDTO.setPreferredQualifications(request.getParameter("qualifications"));
            courseDTO.setDeliveryMethod(request.getParameter("deliveryMethod"));
            courseDTO.setCompensation(Double.valueOf(request.getParameter("compensation")));
            courseDTO.setInstitutionId(institutionId);
            courseDAO.insertCourse(courseDTO);
            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("Create a course detail successfully for institution ID: {}", institutionId);
        } catch (Exception e) {
            logger.error("Error creating a course detail for institution ID: {}", institutionId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching professional profile");
        }
    }
}
