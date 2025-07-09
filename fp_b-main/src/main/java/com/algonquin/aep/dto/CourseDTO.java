package com.algonquin.aep.dto;

/**
 * Represents a Course in the academic system.
 * This class contains comprehensive information about a course offering,
 * including its basic details, scheduling information, requirements, and compensation.
 * Used for both creating new courses and displaying course information to academic professionals.
 */
public class CourseDTO {
    /** Unique identifier for the course */
    private Integer courseId;
    
    /** ID of the institution offering this course */
    private Integer institutionId;
    
    /** Name of the institution offering this course */
    private String institutionName;
    
    /** Full title of the course */
    private String title;
    
    /** Course code used by the institution */
    private String code;
    
    /** Academic term when the course is offered */
    private String term;
    
    /** Course outline/description */
    private String outline;
    
    /** Course schedule information */
    private String schedule;
    
    /** Required or preferred qualifications for teaching this course */
    private String preferredQualifications;
    
    /** Method of course delivery (e.g., In-Person, Online, Hybrid) */
    private String deliveryMethod;
    
    /** Compensation offered for teaching this course */
    private Double compensation;

    /** Default constructor */
    public CourseDTO() {}

    /**
     * Constructs a new CourseDTO with all fields.
     *
     * @param courseId Unique identifier for the course
     * @param institutionId ID of the offering institution
     * @param institutionName Name of the offering institution
     * @param title Course title
     * @param code Course code
     * @param term Academic term
     * @param outline Course outline
     * @param schedule Course schedule
     * @param preferredQualifications Required qualifications
     * @param deliveryMethod Method of delivery
     * @param compensation Teaching compensation
     */
    public CourseDTO(Integer courseId, Integer institutionId, String institutionName, String title, String code, String term, String outline, String schedule, String preferredQualifications, String deliveryMethod, Double compensation) {
        this.courseId = courseId;
        this.institutionId = institutionId;
        this.institutionName = institutionName;
        this.title = title;
        this.code = code;
        this.term = term;
        this.outline = outline;
        this.schedule = schedule;
        this.preferredQualifications = preferredQualifications;
        this.deliveryMethod = deliveryMethod;
        this.compensation = compensation;
    }

    /**
     * Gets the course's unique identifier.
     * @return The course ID
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * Sets the course's unique identifier.
     * @param courseId The course ID to set
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the institution's ID.
     * @return The institution ID
     */
    public Integer getInstitutionId() {
        return institutionId;
    }

    /**
     * Sets the institution's ID.
     * @param institutionId The institution ID to set
     */
    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    /**
     * Gets the institution's name.
     * @return The institution name
     */
    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * Sets the institution's name.
     * @param institutionName The institution name to set
     */
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    /**
     * Gets the course title.
     * @return The course title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the course title.
     * @param title The course title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the course code.
     * @return The course code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code.
     * @param code The course code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the academic term.
     * @return The academic term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the academic term.
     * @param term The academic term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Gets the course outline.
     * @return The course outline
     */
    public String getOutline() {
        return outline;
    }

    /**
     * Sets the course outline.
     * @param outline The course outline to set
     */
    public void setOutline(String outline) {
        this.outline = outline;
    }

    /**
     * Gets the course schedule.
     * @return The course schedule
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the course schedule.
     * @param schedule The course schedule to set
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets the required or preferred qualifications.
     * @return The required or preferred qualifications
     */
    public String getPreferredQualifications() {
        return preferredQualifications;
    }

    /**
     * Sets the required or preferred qualifications.
     * @param preferredQualifications The required or preferred qualifications to set
     */
    public void setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
    }

    /**
     * Gets the method of delivery.
     * @return The method of delivery
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the method of delivery.
     * @param deliveryMethod The method of delivery to set
     */
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    /**
     * Gets the teaching compensation.
     * @return The teaching compensation
     */
    public Double getCompensation() {
        return compensation;
    }

    /**
     * Sets the teaching compensation.
     * @param compensation The teaching compensation to set
     */
    public void setCompensation(Double compensation) {
        this.compensation = compensation;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseId=" + courseId +
                ", institutionId=" + institutionId +
                ", institutionName='" + institutionName + '\'' +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", term='" + term + '\'' +
                ", schedule='" + schedule + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                '}';
    }
}
