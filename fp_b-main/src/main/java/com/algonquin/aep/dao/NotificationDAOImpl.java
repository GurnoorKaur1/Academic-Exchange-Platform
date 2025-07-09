package com.algonquin.aep.dao;

import com.algonquin.aep.dto.NotificationDTO;
import com.algonquin.aep.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the NotificationDAO interface.
 * This class provides concrete implementations for managing notification data
 * in the database, including creating notifications and managing their status.
 */
public class NotificationDAOImpl implements NotificationDAO {
    /** SQL query to create a new notification */
    private static final String CREATE_NOTIFICATION = "INSERT INTO notifications (user_id, message, is_read) VALUES (?, ?, false)";
    
    /** SQL query to update a notification */
    private static final String UPDATE_NOTIFICATION = "UPDATE notifications SET is_read = ? WHERE notification_id = ?";
    
    /** SQL query to find a notification by ID */
    private static final String FIND_BY_ID = "SELECT * FROM notifications WHERE notification_id = ?";
    
    /** SQL query to find unread notifications by user ID */
    private static final String FIND_UNREAD_BY_USER = "SELECT * FROM notifications WHERE user_id = ? AND is_read = false ORDER BY created_at DESC";

    private Connection connection;

    /**
     * Constructs a new NotificationDAOImpl instance.
     * Initializes the database connection.
     */
    public NotificationDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * Creates a new notification for a user with the specified message.
     * 
     * @param userId The ID of the user to create the notification for
     * @param message The message to be included in the notification
     * @return True if the notification was created successfully, false otherwise
     */
    @Override
    public boolean create(Integer userId, String message) {
        try {
            PreparedStatement stmt = connection.prepareStatement(CREATE_NOTIFICATION, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * Updates the status of an existing notification.
     * 
     * @param notification The notification to be updated
     * @return True if the notification was updated successfully, false otherwise
     */
    @Override
    public boolean update(NotificationDTO notification) {
        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_NOTIFICATION);
            
            stmt.setBoolean(1, notification.getIsRead());
            stmt.setInt(2, notification.getNotificationId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * Retrieves a notification by its unique identifier.
     * 
     * @param notificationId The ID of the notification to retrieve
     * @return The notification with the specified ID, or null if not found
     */
    @Override
    public NotificationDTO findById(Integer notificationId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, notificationId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * Retrieves all unread notifications for a specific user.
     * 
     * @param userId The ID of the user to retrieve notifications for
     * @return A list of unread notifications for the specified user
     */
    @Override
    public List<NotificationDTO> findUnreadByUserId(Integer userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(FIND_UNREAD_BY_USER);
            stmt.setInt(1, userId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    /**
     * Maps a ResultSet row to a NotificationDTO object.
     * 
     * @param rs The ResultSet containing the notification data
     * @return A populated NotificationDTO object
     * @throws SQLException if a database access error occurs
     */
    private NotificationDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(rs.getInt("notification_id"));
        dto.setUserId(rs.getInt("user_id"));
        dto.setMessage(rs.getString("message"));
        dto.setIsRead(rs.getBoolean("is_read"));
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        return dto;
    }
}
