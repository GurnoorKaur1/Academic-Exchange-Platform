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
 * The servlet handle the request to update a course information.
 */
@WebServlet("/updateCourse")
public class UpdateCourseServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateCourseServlet.class);
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        courseDAO = new CourseDAOImpl();
    }

    /**
     * Get data from http request and set update a course information by the data inputted.
     *
     * @param request  http request
     * @param response http response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int institutionId = (int) session.getAttribute("userId");

        logger.info("Update a course detail for institution ID: {}", institutionId);

        try {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseId(Integer.parseInt(request.getParameter("courseId")));
            courseDTO.setTitle(request.getParameter("courseTitle"));
            courseDTO.setCode(request.getParameter("courseCode"));
            courseDTO.setTerm(request.getParameter("term"));
            courseDTO.setOutline(request.getParameter("courseOutline"));
            courseDTO.setSchedule(request.getParameter("schedule"));
            courseDTO.setPreferredQualifications(request.getParameter("qualifications"));
            courseDTO.setDeliveryMethod(request.getParameter("deliveryMethod"));
            courseDTO.setCompensation(Double.valueOf(request.getParameter("compensation")));
            courseDAO.updateCourse(courseDTO);

            response.setStatus(HttpServletResponse.SC_OK);
            logger.info("Update a course successfully for institution ID: {}", institutionId);
        } catch (Exception e) {
            logger.error("Error updating a course for institution ID: {}", institutionId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching professional profile");
        }
    }
}
