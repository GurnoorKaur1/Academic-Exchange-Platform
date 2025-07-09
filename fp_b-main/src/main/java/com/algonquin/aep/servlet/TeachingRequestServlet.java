package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.*;
import com.algonquin.aep.dto.TeachingRequestDTO;
import com.algonquin.aep.dto.CourseDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Servlet responsible for managing teaching requests in the Academic Expert Platform.
 * This servlet handles the creation, retrieval, and status updates of teaching requests
 * between academic professionals and institutions.
 *
 * Supported operations:
 * - GET: Retrieve teaching requests (filtered by user type)
 * - POST: Create new teaching request (for professionals)
 * - PUT: Update teaching request status (for institutions)
 *
 * URL Pattern: /api/teaching-request/*
 * Authentication: Required for all operations
 */
@WebServlet("/api/teaching-request/*")
public class TeachingRequestServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TeachingRequestServlet.class.getName());
    private TeachingRequestDAO teachingRequestDAO;
    private NotificationDAO notificationDAO;
    private CourseDAO courseDAO;
    private Gson gson;

    /**
     * Initializes the servlet by creating instances of required DAO components and utilities.
     * This method is called by the servlet container when the servlet is first loaded.
     * It initializes the TeachingRequest, Notification, and Course DAOs, as well as the Gson parser.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        teachingRequestDAO = new TeachingRequestDAOImpl();
        notificationDAO = new NotificationDAOImpl();
        courseDAO = new CourseDAOImpl();
        gson = new Gson();
    }

    /**
     * Handles GET requests to retrieve teaching requests. The response is filtered based on
     * the user type (professional or institution) stored in the session.
     *
     * For institutions: Returns all teaching requests received by the institution
     * For professionals: Returns all teaching requests made by the professional
     *
     * Response format: JSON array of teaching request objects
     *
     * @param request The HTTP servlet request
     * @param response The HTTP servlet response
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userType = (String) session.getAttribute("userType");
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        response.setContentType("application/json");
        if ("institution".equals(userType)) {
            // Get requests for institution
            var requests = teachingRequestDAO.findByInstitutionId(userId);
            response.getWriter().write(gson.toJson(requests));
        } else if ("professional".equals(userType)) {
            // Get requests made by professional
            var requests = teachingRequestDAO.findByProfessionalId(userId);
            response.getWriter().write(gson.toJson(requests));
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * Handles PUT requests to update the status of a teaching request.
     * Only institutions can update the status of teaching requests.
     * When a request is updated, a notification is sent to the professional.
     *
     * Request body format (JSON):
     * {
     *   "requestId": number,
     *   "status": string ("Accepted" or "Rejected")
     * }
     *
     * Response status codes:
     * - 200: Success
     * - 400: Invalid status
     * - 401: Unauthorized
     * - 404: Teaching request not found
     * - 500: Server error during update
     *
     * @param request The HTTP servlet request containing the update data
     * @param response The HTTP servlet response
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer institutionId = (Integer) session.getAttribute("userId");
        
        if (institutionId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Read JSON data from request body
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        
        // Parse JSON data
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        Integer requestId = jsonObject.get("requestId").getAsInt();
        String status = jsonObject.get("status").getAsString();

        if (!status.equals("Accepted") && !status.equals("Rejected")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status");
            return;
        }

        TeachingRequestDTO teachingRequest = teachingRequestDAO.findById(requestId);
        if (teachingRequest != null) {
            teachingRequest.setStatus(status);
            boolean success = teachingRequestDAO.update(teachingRequest);
            
            if (success) {
                // Get course information
                CourseDTO course = courseDAO.findCourseById(teachingRequest.getCourseId());
                
                // Create notification for the professional with course details
                String message = String.format("Your teaching request for course %s - %s has been %s", 
                    course.getCode(), course.getTitle(), status.toLowerCase());
                notificationDAO.create(teachingRequest.getProfessionalId(), message);
                
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Teaching request updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Failed to update teaching request\"}");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles POST requests to create a new teaching request.
     * Only professionals can create teaching requests.
     * The request is automatically set to "Pending" status upon creation.
     *
     * Required parameters:
     * - courseId: ID of the course being requested to teach
     *
     * Response status codes:
     * - 201: Created successfully
     * - 401: Unauthorized
     * - 500: Server error during creation
     *
     * @param request The HTTP servlet request containing the course ID
     * @param response The HTTP servlet response
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer professionalId = (Integer) session.getAttribute("userId");
        
        if (professionalId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Integer courseId = Integer.parseInt(request.getParameter("courseId"));
        
        TeachingRequestDTO teachingRequest = new TeachingRequestDTO();
        teachingRequest.setProfessionalId(professionalId);
        teachingRequest.setCourseId(courseId);
        teachingRequest.setStatus("Pending");
        
        boolean success = teachingRequestDAO.create(teachingRequest);
        
        response.setContentType("application/json");
        if (success) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Teaching request created successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to create teaching request\"}");
        }
    }
}
