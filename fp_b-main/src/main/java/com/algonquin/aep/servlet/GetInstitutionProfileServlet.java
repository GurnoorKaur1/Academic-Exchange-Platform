package com.algonquin.aep.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.algonquin.aep.dao.AcademicInstitutionDAO;
import com.algonquin.aep.dao.AcademicInstitutionDAOImpl;
import com.algonquin.aep.dto.AcademicInstitutionDTO;
import com.algonquin.aep.dto.UserDTO;
import com.algonquin.aep.dao.UserDAO;
import com.algonquin.aep.dao.UserDAOImpl;

/**
 * Servlet responsible for handling requests to retrieve institution profile information.
 * This servlet processes GET requests to fetch both institution-specific data and associated
 * user account information, combining them into a single JSON response. The data is retrieved
 * based on the user ID stored in the session.
 */
@WebServlet("/getInstitutionProfile")
public class GetInstitutionProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetInstitutionProfileServlet.class.getName());
    private AcademicInstitutionDAO institutionDAO;
    private UserDAO userDAO;

    /**
     * Initializes the servlet by creating instances of the required DAOs.
     * This method is called by the servlet container when the servlet is first loaded.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        institutionDAO = new AcademicInstitutionDAOImpl();
        userDAO = new UserDAOImpl();
        logger.info("GetInstitutionProfileServlet initialized");
    }

    /**
     * Handles GET requests to retrieve institution profile information.
     * Retrieves both institution-specific data and user account information,
     * combines them into a JSON response containing:
     * - Institution name and address
     * - User email, type, and account creation date
     *
     * The method performs the following steps:
     * 1. Extracts user ID from the session
     * 2. Retrieves institution data using AcademicInstitutionDAO
     * 3. Retrieves user data using UserDAO
     * 4. Combines the data into a JSON format
     * 5. Sends the JSON response back to the client
     *
     * @param request The HTTP servlet request containing the session with user ID
     * @param response The HTTP servlet response used to send the JSON data
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received GET request for institution profile");

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        logger.info("Fetching profile for user ID: " + userId);

        try {
            AcademicInstitutionDTO institution = institutionDAO.findInstitutionById(userId);
            UserDTO user = userDAO.findUserById(userId);
            logger.info("Retrieved institution and user data for ID: " + userId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            
            String jsonResponse = String.format(
                "{\"name\":\"%s\",\"email\":\"%s\",\"type\":\"%s\",\"creationDate\":\"%s\",\"address\":\"%s\"}",
                institution.getName(),
                user.getEmail(),
                user.getUserType(),
                user.getCreatedAt(),
                institution.getAddress()
            );
            
            out.print(jsonResponse);
            out.flush();
            logger.info("Successfully sent JSON response for institution profile");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching or sending institution profile", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while processing your request");
        }
    }
}
