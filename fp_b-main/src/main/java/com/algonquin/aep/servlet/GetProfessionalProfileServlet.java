package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.AcademicProfessionalDAO;
import com.algonquin.aep.dao.AcademicProfessionalDAOImpl;
import com.algonquin.aep.dto.AcademicProfessionalDTO;
import com.algonquin.aep.dto.UserDTO;
import com.algonquin.aep.dao.UserDAO;
import com.algonquin.aep.dao.UserDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet responsible for handling requests to retrieve professional profile information.
 * This servlet processes GET requests to fetch both professional-specific data and associated
 * user account information, combining them into a single JSON response. The data is retrieved
 * based on the user ID stored in the session.
 * 
 * The profile information includes:
 * - Basic user information (email, type, creation date)
 * - Professional details (name, position, institution)
 * - Academic information (education background, area of expertise)
 */
@WebServlet("/getProfessionalProfile")
public class GetProfessionalProfileServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(GetProfessionalProfileServlet.class);
    private AcademicProfessionalDAO professionalDAO;
    private UserDAO userDAO;

    /**
     * Initializes the servlet by creating instances of the required DAOs.
     * This method is called by the servlet container when the servlet is first loaded.
     * It initializes both the professional and user data access objects needed for
     * retrieving profile information.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        super.init();
        professionalDAO = new AcademicProfessionalDAOImpl();
        userDAO = new UserDAOImpl();
    }

    /**
     * Handles GET requests to retrieve professional profile information.
     * Retrieves both professional-specific data and user account information,
     * combines them into a JSON response containing:
     * - Name and email
     * - User type and account creation date
     * - Current position and institution
     * - Education background and area of expertise
     *
     * The method performs the following steps:
     * 1. Extracts user ID from the session
     * 2. Retrieves professional data using AcademicProfessionalDAO
     * 3. Retrieves user data using UserDAO
     * 4. Validates that both records exist
     * 5. Combines the data into a JSON format
     * 6. Sends the JSON response back to the client
     *
     * @param request The HTTP servlet request containing the session with user ID
     * @param response The HTTP servlet response used to send the JSON data
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        
        logger.info("Fetching professional profile for user ID: {}", userId);

        try {
            AcademicProfessionalDTO professional = professionalDAO.findProfessionalById(userId);
            UserDTO user = userDAO.findUserById(userId);

            if (professional == null || user == null) {
                logger.warn("Professional or user not found for ID: {}", userId);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Professional profile not found");
                return;
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            
            String jsonResponse = String.format(
                "{\"name\":\"%s\",\"email\":\"%s\",\"type\":\"%s\",\"creationDate\":\"%s\",\"currentPosition\":\"%s\",\"currentInstitution\":\"%s\",\"educationBackground\":\"%s\",\"areaOfExpertise\":\"%s\"}",
                professional.getName(),
                user.getEmail(),
                user.getUserType(),
                user.getCreatedAt(),
                professional.getAcademicPosition(),
                professional.getCurrentInstitution(),
                professional.getEducationBackground(),
                professional.getAreaOfExpertise()
            );
            
            out.print(jsonResponse);
            out.flush();
            logger.info("Professional profile sent successfully for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error fetching professional profile for user ID: {}", userId, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching professional profile");
        }
    }
}
