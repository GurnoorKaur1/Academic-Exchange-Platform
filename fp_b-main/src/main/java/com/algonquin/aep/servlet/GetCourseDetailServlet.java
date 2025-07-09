package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import com.google.gson.Gson;
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
 * Servlet responsible for handling requests to retrieve detailed information about a specific course.
 * Maps to the URL pattern "/getCourseDetails"
 */
@WebServlet("/getCourseDetails")
public class GetCourseDetailServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetCourseDetailServlet.class);
    private CourseDAO courseDAO;

    /**
     * Initializes the servlet and creates a new instance of CourseDAO
     */
    @Override
    public void init() throws ServletException {
        super.init();
        courseDAO = new CourseDAOImpl();
    }

    /**
     * Handles GET requests to retrieve course details
     * @param request The HTTP request containing the courseId parameter
     * @param response The HTTP response that will contain the course details in JSON format
     * @throws IOException If an input or output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int institutionId = (int) session.getAttribute("userId");

        logger.info("Retrieve a course detail for institution ID: {}", institutionId);

        try {
            CourseDTO course = courseDAO.findCourseById(Integer.parseInt(request.getParameter("courseId")));
            Gson gson = new Gson();
            String jsonCourses = gson.toJson(course);
            logger.info("Retrieved course details:{}", jsonCourses);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonCourses);
            logger.info("Successfully sent JSON response");
        } catch (Exception e) {
            logger.error("Error creating a course detail for institution ID: {}", institutionId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching professional profile");
        }
    }
}
