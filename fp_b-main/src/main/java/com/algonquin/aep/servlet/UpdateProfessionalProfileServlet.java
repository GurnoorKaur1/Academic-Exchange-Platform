package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.AcademicProfessionalDAO;
import com.algonquin.aep.dao.AcademicProfessionalDAOImpl;
import com.algonquin.aep.dto.AcademicProfessionalDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet responsible for handling professional profile updates in the Academic Expert Platform.
 * This servlet processes PUT requests to update professional profile information.
 *
 * URL Pattern: /api/professional-profile
 * Method: PUT
 * Authentication: Required
 */
@WebServlet("/api/professional-profile")
public class UpdateProfessionalProfileServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateProfessionalProfileServlet.class);
    private AcademicProfessionalDAO professionalDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        professionalDAO = new AcademicProfessionalDAOImpl();
        gson = new Gson();
    }

    /**
     * Handles PUT requests to update professional profile information.
     * The user must be authenticated and the profile ID must match the session user ID.
     *
     * Request body format (JSON):
     * {
     *   "name": string,
     *   "currentPosition": string,
     *   "currentInstitution": string,
     *   "educationBackground": string,
     *   "areaOfExpertise": string
     * }
     *
     * Response status codes:
     * - 200: Success
     * - 400: Invalid request data
     * - 401: Unauthorized
     * - 500: Server error during update
     *
     * @param request The HTTP servlet request containing profile data
     * @param response The HTTP servlet response
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Read JSON data from request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
            
            AcademicProfessionalDTO professional = new AcademicProfessionalDTO();
            professional.setProfessionalId(userId);
            professional.setName(jsonObject.get("name").getAsString());
            professional.setCurrentInstitution(jsonObject.get("currentInstitution").getAsString());
            professional.setAcademicPosition(jsonObject.get("currentPosition").getAsString());
            professional.setEducationBackground(jsonObject.get("educationBackground").getAsString());
            professional.setAreaOfExpertise(jsonObject.get("areaOfExpertise").getAsString());

            boolean success = professionalDAO.updateProfessional(professional);
            
            response.setContentType("application/json");
            if (success) {
                response.getWriter().write("{\"message\": \"Profile updated successfully\"}");
                logger.info("Professional profile updated successfully for user ID: {}", userId);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Failed to update profile\"}");
                logger.error("Failed to update professional profile for user ID: {}", userId);
            }
        } catch (Exception e) {
            logger.error("Error updating professional profile for user ID: {}", userId, e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid profile data\"}");
        }
    }
}
