package com.algonquin.aep.dao;

import com.algonquin.aep.dto.NotificationDTO;
import java.util.List;

/**
 * Data Access Object interface for managing Notification data.
 * Provides methods to handle notifications in the system, including creating,
 * retrieving, and managing notification records for users.
 */
public interface NotificationDAO {
    /**
     * Creates a new notification in the database.
     * 
     * @param userId The ID of the user
     * @param message The message of the notification
     * @return True if the notification is created successfully, false otherwise
     */
    boolean create(Integer userId, String message);

    /**
     * Updates the notification in the database.
     * 
     * @param notification The notification data to be updated
     * @return True if the notification is updated successfully, false otherwise
     */
    boolean update(NotificationDTO notification);

    /**
     * Retrieves a notification by its ID.
     * 
     * @param notificationId The ID of the notification
     * @return The notification data
     */
    NotificationDTO findById(Integer notificationId);

    /**
     * Retrieves all unread notifications for a specific user.
     * 
     * @param userId The ID of the user
     * @return List of unread notifications for the user
     */
    List<NotificationDTO> findUnreadByUserId(Integer userId);
}
