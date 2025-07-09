package com.algonquin.aep.dto;

import java.sql.Timestamp;

/**
 * Data Transfer Object representing a Notification.
 * This class is used to transfer notification data between different layers of the application.
 */
public class NotificationDTO {
    private Integer notificationId;
    private Integer userId;
    private String message;
    private Boolean isRead;
    private Timestamp createdAt;

    /**
     * Gets the notification ID.
     *
     * @return The unique identifier of the notification
     */
    public Integer getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification ID.
     *
     * @param notificationId The unique identifier to set for the notification
     */
    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the user ID associated with this notification.
     *
     * @return The ID of the user who should receive this notification
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for this notification.
     *
     * @param userId The ID of the user who should receive this notification
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the notification message.
     *
     * @return The content of the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message.
     *
     * @param message The content to set for the notification message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the read status of the notification.
     *
     * @return true if the notification has been read, false otherwise
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * Sets the read status of the notification.
     *
     * @param isRead true to mark the notification as read, false to mark as unread
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * Gets the creation timestamp of the notification.
     *
     * @return The timestamp when this notification was created
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the notification.
     *
     * @param createdAt The timestamp to set for when this notification was created
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
