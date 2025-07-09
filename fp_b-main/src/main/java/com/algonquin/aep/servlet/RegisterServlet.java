package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.*;
import com.algonquin.aep.dto.UserDTO;
import com.algonquin.aep.dto.AcademicProfessionalDTO;
import com.algonquin.aep.dto.AcademicInstitutionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet responsible for handling user registration in the Academic Expert Platform.
 * This servlet processes registration requests for both academic professionals and institutions,
 * creating appropriate user profiles based on the user type.
 *
 * The registration process includes:
 * 1. Creating a base user account with email and password
 * 2. Creating a specific profile (professional or institution) based on user type
 * 3. Storing all information in the database
 *
 * URL Pattern: /register
 * Method: POST
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);
    private UserDAO userDAO;
    private AcademicProfessionalDAO professionalDAO;
    private AcademicInstitutionDAO institutionDAO;

    /**
     * Initializes the servlet by creating instances of required DAO components.
     * This method is called by the servlet container when the servlet is first loaded.
     * It initializes the User, AcademicProfessional, and AcademicInstitution DAOs.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        professionalDAO = new AcademicProfessionalDAOImpl();
        institutionDAO = new AcademicInstitutionDAOImpl();
        logger.info("RegisterServlet initialized");
    }

    /**
     * Handles POST requests for user registration. Processes the registration form data
     * and creates appropriate user profiles based on the user type.
     *
     * Expected form parameters:
     * - email: User's email address
     * - password: User's password
     * - userType: Type of user ("professional" or "institution")
     * 
     * For professional users:
     * - name: Professional's full name
     * - currentInstitution: Current institution affiliation
     * - academicPosition: Current academic position
     *
     * For institution users:
     * - name: Institution name
     *
     * On success, redirects to login.jsp
     * On failure, redirects to register.jsp with error parameter
     *
     * @param request The HTTP servlet request containing registration form data
     * @param response The HTTP servlet response
     * @throws ServletException If there is an error in servlet processing
     * @throws IOException If there is an error in I/O operations
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        logger.info("Received registration request for email: {} and user type: {}", email, userType);

        UserDTO newUser = new UserDTO();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setUserType(userType);

        try {
            userDAO.insertUser(newUser);
            logger.info("User inserted successfully with ID: {}", newUser.getUserId());

            if ("professional".equals(userType)) {
                AcademicProfessionalDTO professional = new AcademicProfessionalDTO();
                professional.setProfessionalId(newUser.getUserId());
                professional.setName(request.getParameter("name"));
                professional.setCurrentInstitution(request.getParameter("currentInstitution"));
                professional.setAcademicPosition(request.getParameter("academicPosition"));
                professionalDAO.insertProfessional(professional);
                logger.info("Professional profile created for user ID: {}", newUser.getUserId());
            } else if ("institution".equals(userType)) {
                AcademicInstitutionDTO institution = new AcademicInstitutionDTO();
                institution.setInstitutionId(newUser.getUserId());
                institution.setName(request.getParameter("name"));
                institutionDAO.insertInstitution(institution);
                logger.info("Institution profile created for user ID: {}", newUser.getUserId());
            }

            logger.info("Registration successful for email: {}", email);
            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            logger.error("Error during registration for email: {}", email, e);
            response.sendRedirect("register.jsp?error=1");
        }
    }
}
