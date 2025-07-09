package com.algonquin.aep.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LogoutServlet handles the user logout process in the AEP system.
 * This servlet invalidates the user's session and redirects them to the login page.
 * It also logs the logout event for security and monitoring purposes.
 *
 * @author Baichuan Chen
 * @version 1.0
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LogoutServlet.class);

    /**
     * Handles GET requests for user logout. Invalidates the user's session if it exists
     * and redirects to the login page. The method logs both the logout event and the
     * subsequent redirect for tracking purposes.
     *
     * @param request  the HttpServletRequest object containing client request information
     * @param response the HttpServletResponse object for sending response to client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("User logged out: {}", session.getAttribute("userEmail"));
            session.invalidate();
        }
        logger.info("Redirecting to login page after logout");
        response.sendRedirect("login.jsp");
    }
}
