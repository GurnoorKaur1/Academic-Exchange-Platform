package com.algonquin.aep.dto;

/**
 * Data Transfer Object for course search criteria.
 * This class encapsulates all the search parameters that can be used to filter courses
 * in the course search functionality. Each field represents a different search criterion
 * that can be used independently or in combination with others.
 */
public class CourseSearchDTO {
    /** Name of the institution to filter courses by */
    private String institutionName;
    
    /** Course code to search for */
    private String courseCode;
    
    /** Course title to search for */
    private String courseTitle;
    
    /** Academic term to filter courses by */
    private String term;
    
    /** Schedule preference to filter courses by */
    private String schedule;
    
    /** Delivery method preference to filter courses by */
    private String deliveryMethod;

    /** Default constructor */
    public CourseSearchDTO() {
    }

    /**
     * Gets the institution name filter.
     * @return The institution name
     */
    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets the institution name filter.
     * @param institutionName The institution name to filter by
     */
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    /**
     * Gets the course code filter.
     * @return The course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Sets the course code filter.
     * @param courseCode The course code to filter by
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Gets the course title filter.
     * @return The course title
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * Sets the course title filter.
     * @param courseTitle The course title to filter by
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * Gets the term filter.
     * @return The academic term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the term filter.
     * @param term The academic term to filter by
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Gets the schedule filter.
     * @return The schedule preference
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule filter.
     * @param schedule The schedule to filter by
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets the delivery method filter.
     * @return The delivery method preference
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the delivery method filter.
     * @param deliveryMethod The delivery method to filter by
     */
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "CourseSearchDTO{" +
                "institutionName='" + institutionName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", term='" + term + '\'' +
                ", schedule='" + schedule + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                '}';
    }
}