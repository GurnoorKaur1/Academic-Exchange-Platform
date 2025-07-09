package com.algonquin.aep.servlet;

import com.algonquin.aep.dao.NotificationDAO;
import com.algonquin.aep.dao.NotificationDAOImpl;
import com.algonquin.aep.dto.NotificationDTO;
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
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet responsible for handling notification-related operations.
 * This servlet provides endpoints for retrieving unread notifications and updating
 * notification read status. All operations require user authentication via session.
 *
 * Supported operations:
 * - GET: Retrieve all unread notifications for the authenticated user
 * - PUT: Update the read status of a specific notification
 *
 * URL Pattern: /api/notifications/*
 * For PUT requests, the notification ID should be included in the URL path
 * (e.g., /api/notifications/123)
 */
@WebServlet("/api/notifications/*")
public class NotificationServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(NotificationServlet.class.getName());
    private NotificationDAO notificationDAO;
    private Gson gson;

    /**
     * Initializes the servlet by creating instances of required components.
     * This method is called by the servlet container when the servlet is first loaded.
     * It initializes the notification DAO and Gson parser for JSON handling.
     *
     * @throws ServletException If there is an error initializing the servlet
     */
    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAOImpl();
        gson = new Gson();
    }

    /**
     * Handles GET requests to retrieve unread notifications for the authenticated user.
     * Returns a JSON array of notification objects. Each notification includes
     * message content, creation timestamp, and read status.
     *
     * Authentication is required via session userId.
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
        
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<NotificationDTO> notifications = notificationDAO.findUnreadByUserId(userId);
        
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(notifications));
    }

    /**
     * Handles PUT requests to update the read status of a specific notification.
     * The notification ID must be provided in the URL path, and the new read status
     * must be provided in the request body as JSON.
     *
     * Request URL format: /api/notifications/{notificationId}
     * Request body format: {"isRead": boolean}
     *
     * Authentication is required via session userId. Users can only update
     * notifications that belong to them.
     *
     * Response status codes:
     * - 200: Success
     * - 400: Invalid notification ID
     * - 401: Unauthorized
     * - 404: Notification not found or doesn't belong to user
     * - 500: Server error during update
     *
     * @param request The HTTP servlet request containing notification ID and new status
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

        // Get notification ID from URL path
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.matches("/\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid notification ID");
            return;
        }
        Integer notificationId = Integer.parseInt(pathInfo.substring(1));

        // Read JSON body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        boolean isRead = jsonObject.get("isRead").getAsBoolean();

        NotificationDTO notification = notificationDAO.findById(notificationId);
        
        if (notification != null && notification.getUserId().equals(userId)) {
            notification.setIsRead(isRead);
            boolean success = notificationDAO.update(notification);
            
            response.setContentType("application/json");
            if (success) {
                response.getWriter().write("{\"message\": \"Notification marked as read\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Failed to mark notification as read\"}");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
