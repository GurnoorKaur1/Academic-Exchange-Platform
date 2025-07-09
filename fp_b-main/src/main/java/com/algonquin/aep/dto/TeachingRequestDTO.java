package com.algonquin.aep.dto;

import java.sql.Timestamp;

/**
 * Data Transfer Object representing a Teaching Request.
 * This class is used to transfer teaching request data between different layers of the application,
 * including both basic request information and additional course and professional details.
 */
public class TeachingRequestDTO {
    private Integer requestId;
    private Integer professionalId;
    private Integer courseId;
    private String status;
    private Timestamp createdAt;
    
    // Additional fields for course and professional information
    private String courseCode;
    private String courseTitle;
    private String professionalName;

    /**
     * Gets the request ID.
     *
     * @return The unique identifier of the teaching request
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Sets the request ID.
     *
     * @param requestId The unique identifier to set for the teaching request
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets the professional ID associated with this request.
     *
     * @return The ID of the professional who made this teaching request
     */
    public Integer getProfessionalId() {
        return professionalId;
    }

    /**
     * Sets the professional ID for this request.
     *
     * @param professionalId The ID of the professional who made this teaching request
     */
    public void setProfessionalId(Integer professionalId) {
        this.professionalId = professionalId;
    }

    /**
     * Gets the course ID associated with this request.
     *
     * @return The ID of the course for which this teaching request was made
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID for this request.
     *
     * @param courseId The ID of the course for which this teaching request is made
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the status of the teaching request.
     *
     * @return The current status of the teaching request (e.g., "pending", "approved", "rejected")
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the teaching request.
     *
     * @param status The status to set for the teaching request
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the creation timestamp of the request.
     *
     * @return The timestamp when this teaching request was created
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the request.
     *
     * @param createdAt The timestamp to set for when this teaching request was created
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the course code.
     *
     * @return The code identifier of the course
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code.
     *
     * @param courseCode The code identifier to set for the course
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the course title.
     *
     * @return The title of the course
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the course title.
     *
     * @param courseTitle The title to set for the course
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Gets the professional's name.
     *
     * @return The name of the professional who made the teaching request
     */
    public String getProfessionalName() {
        return professionalName;
    }

    /**
     * Sets the professional's name.
     *
     * @param professionalName The name to set for the professional
     */
    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }
}
