package com.algonquin.aep.servlet;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet responsible for handling requests to retrieve course offerings.
 * This servlet processes GET requests to fetch all courses associated with 
 * a specific institution, identified by the institution ID stored in the session.
 * The course data is returned as a JSON response.
 */
@WebServlet("/getCourseOfferings")
public class GetCourseOfferingsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetCourseOfferingsServlet.class.getName());

    /**
     * Handles GET requests to retrieve course offerings for an institution.
     * Retrieves the institution ID from the session, fetches associated courses
     * from the database, and returns them as a JSON response.
     *
     * The method performs the following steps:
     * 1. Extracts institution ID from the session
     * 2. Queries the database for courses associated with the institution
     * 3. Converts the course list to JSON format
     * 4. Sends the JSON response back to the client
     *
     * @param request The HTTP servlet request containing the session with institution ID
     * @param response The HTTP servlet response used to send the JSON data
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received GET request for course offerings");

        HttpSession session = request.getSession();
        int institutionId = (int) session.getAttribute("userId");
        logger.info("Fetching courses for institution ID: " + institutionId);

        CourseDAO courseDAO = new CourseDAOImpl();
        try {
            List<CourseDTO> courses = courseDAO.getCoursesByInstitutionId(institutionId);
            logger.info("Retrieved " + courses.size() + " courses");

            Gson gson = new Gson();
            String jsonCourses = gson.toJson(courses);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonCourses);
            logger.info("Successfully sent JSON response");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching or sending course offerings", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while processing your request");
        }
    }
}
