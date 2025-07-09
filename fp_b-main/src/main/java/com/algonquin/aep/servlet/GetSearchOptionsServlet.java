package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet responsible for handling requests to retrieve various search options and metadata
 * related to courses and institutions. This servlet supports different types of queries
 * based on the 'type' parameter, allowing clients to fetch institutions, course titles,
 * terms, and course codes.
 *
 * Supported query types:
 * - institutions: Returns all available institutions
 * - courseTitle: Returns the title for a specific course code at an institution
 * - terms: Returns available terms for a specific course at an institution
 * - courseCodes: Returns all course codes for a specific institution
 */
@WebServlet("/getSearchOptions")
public class GetSearchOptionsServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetSearchOptionsServlet.class);
    private CourseDAO courseDAO;

    /**
     * Initializes the servlet by creating an instance of CourseDAO.
     * This method is called by the servlet container when the servlet is first loaded.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAOImpl();
        logger.info("GetSearchOptionsServlet initialized");
    }

    /**
     * Handles GET requests to retrieve various search options based on the query type.
     * The method processes different types of queries and returns appropriate data in JSON format.
     *
     * Request parameters:
     * - type: The type of search options to retrieve (institutions, courseTitle, terms, courseCodes)
     * - institutionId: (Optional) The ID of the institution for filtering results
     * - courseCode: (Optional) The course code for retrieving specific course information
     *
     * The response format varies based on the query type:
     * - institutions: List of all institutions
     * - courseTitle: Title of a specific course
     * - terms: Available terms for a specific course
     * - courseCodes: List of course codes for an institution
     *
     * @param request The HTTP servlet request containing query parameters
     * @param response The HTTP servlet response for sending JSON data
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String type = request.getParameter("type");
        String institutionId = request.getParameter("institutionId");
        String courseCode = request.getParameter("courseCode");
        
        logger.info("Received request - type: {}, institutionId: {}, courseCode: {}", 
                type, institutionId, courseCode);
        
        try {
            Object result = null;
            
            switch(type) {
                case "institutions":
                    result = courseDAO.getAllInstitutions();
                    break;
                case "courseTitle":
                    result = courseDAO.getCourseTitleByCode(institutionId, courseCode);
                    break;
                case "terms":
                    result = courseDAO.getTermsByCourseCode(institutionId, courseCode);
                    break;
                case "courseCodes":
                    result = courseDAO.getCourseCodesByInstitution(institutionId);
                    break;
            }
            
            String jsonResponse = new Gson().toJson(result);
            logger.debug("Sending JSON response: {}", jsonResponse);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            
        } catch (Exception e) {
            logger.error("Error processing request: {}", e.getMessage(), e);
            throw new ServletException("Error processing search options", e);
        }
    }
}