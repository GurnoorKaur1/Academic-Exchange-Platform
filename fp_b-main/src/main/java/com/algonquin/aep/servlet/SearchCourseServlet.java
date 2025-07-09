package com.algonquin.aep.servlet;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.algonquin.aep.dao.CourseDAO;
import com.algonquin.aep.dao.CourseDAOImpl;
import com.algonquin.aep.dto.CourseDTO;
import com.algonquin.aep.dto.CourseSearchDTO;
import com.google.gson.Gson;

/**
 * Servlet responsible for handling course search requests.
 * Maps to the URL pattern "/searchCourse"
 */
@WebServlet("/searchCourse")
public class SearchCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;
    private static final Logger logger = LogManager.getLogger(SearchCourseServlet.class);

    /**
     * Initializes the servlet and creates a new instance of CourseDAO
     */
    @Override
    public void init() throws ServletException {
        super.init();
        courseDAO = new CourseDAOImpl();
    }

    /**
     * Handles POST requests for course searches
     * @param request The HTTP request containing search parameters (institutionName, courseCode, courseTitle, etc.)
     * @param response The HTTP response that will contain the search results in JSON format
     * @throws ServletException If the request cannot be handled
     * @throws IOException If an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        logger.info("Received search request");
        
        try {
            CourseSearchDTO searchDTO = new CourseSearchDTO();
            searchDTO.setInstitutionName(request.getParameter("institutionName"));
            searchDTO.setCourseCode(request.getParameter("courseCode"));
            searchDTO.setCourseTitle(request.getParameter("courseTitle"));
            searchDTO.setTerm(request.getParameter("term"));
            searchDTO.setSchedule(request.getParameter("schedule"));
            searchDTO.setDeliveryMethod(request.getParameter("deliveryMethod"));
            
            logger.info("Search parameters received:");
            logger.info("institutionName: {}", searchDTO.getInstitutionName());
            logger.info("courseCode: {}", searchDTO.getCourseCode());
            logger.info("courseTitle: {}", searchDTO.getCourseTitle());
            logger.info("term: {}", searchDTO.getTerm());
            logger.info("schedule: {}", searchDTO.getSchedule());
            logger.info("deliveryMethod: {}", searchDTO.getDeliveryMethod());
            
            List<CourseDTO> results = courseDAO.searchCourses(searchDTO);
            logger.info("Found {} matching courses", results.size());
            
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(results);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            
        } catch (Exception e) {
            logger.error("Error processing search request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error processing search request: " + e.getMessage());
        }
    }
}