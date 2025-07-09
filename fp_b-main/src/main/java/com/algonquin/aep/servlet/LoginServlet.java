package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.AcademicInstitutionDAO;
import com.algonquin.aep.dao.AcademicInstitutionDAOImpl;
import com.algonquin.aep.dao.AcademicProfessionalDAO;
import com.algonquin.aep.dao.AcademicProfessionalDAOImpl;
import com.algonquin.aep.dao.UserDAO;
import com.algonquin.aep.dao.UserDAOImpl;
import com.algonquin.aep.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * LoginServlet handles user authentication for both academic professionals and institutions.
 * This servlet processes login requests, validates credentials, and manages user sessions.
 * It redirects users to appropriate dashboards based on their user type after successful authentication.
 *
 * @author Baichuan Chen
 * @version 1.0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private UserDAO userDAO;
    private AcademicProfessionalDAO professionalDAO;
    private AcademicInstitutionDAO institutionDAO;

    /**
     * Initializes the servlet by creating instances of required DAO objects.
     * This method is called by the servlet container when the servlet is first loaded.
     *
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        professionalDAO = new AcademicProfessionalDAOImpl();
        institutionDAO = new AcademicInstitutionDAOImpl();
        logger.info("LoginServlet initialized");
    }

    /**
     * Handles POST requests for user login. Validates user credentials and manages session creation.
     * If authentication is successful, redirects to appropriate dashboard based on user type.
     * If authentication fails, redirects back to login page with error parameter.
     *
     * @param request  the HttpServletRequest object containing client request information
     * @param response the HttpServletResponse object for sending response to client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Received POST request for login");
        String email = request.getParameter("email");
        logger.info("Login attempt for email: " + email);

        String password = request.getParameter("password");

        UserDTO user = userDAO.findUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            logger.info("Login successful for user: " + email);
            
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userType", user.getUserType());
            session.setAttribute("userEmail", user.getEmail());
            
            String name = "";
            if ("professional".equals(user.getUserType())) {
                name = professionalDAO.getProfessionalNameById(user.getUserId());
                logger.info("Redirecting professional user to dashboard");
                response.sendRedirect("professionalDashboard.jsp");
            } else {
                name = institutionDAO.getInstitutionNameById(user.getUserId());
                logger.info("Redirecting institution user to dashboard");
                response.sendRedirect("institutionDashboard.jsp");
            } 
            session.setAttribute("name", name);
        } else {
            logger.warning("Login failed for email: " + email);
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
