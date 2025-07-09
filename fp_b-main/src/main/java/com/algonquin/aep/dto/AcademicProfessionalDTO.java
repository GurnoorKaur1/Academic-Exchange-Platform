package com.algonquin.aep.dto;

/**
 * Represents an Academic Professional in the system.
 * This class contains information about academic professionals who can apply to teach courses,
 * including their personal details, current position, and academic qualifications.
 */
public class AcademicProfessionalDTO {
    /** Unique identifier for the academic professional */
    private int professionalId;
    
    /** Full name of the academic professional */
    private String name;
    
    /** Current institution where the professional is employed */
    private String currentInstitution;
    
    /** Current academic position (e.g., Professor, Associate Professor) */
    private String academicPosition;
    
    /** Educational background and qualifications */
    private String educationBackground;
    
    /** Areas of academic expertise and specialization */
    private String areaOfExpertise;

    /** Default constructor */
    public AcademicProfessionalDTO() {}

    /**
     * Constructs a new AcademicProfessionalDTO with all fields.
     *
     * @param professionalId Unique identifier for the professional
     * @param name Full name of the professional
     * @param currentInstitution Current institution of employment
     * @param academicPosition Current academic position
     * @param educationBackground Educational background details
     * @param areaOfExpertise Areas of expertise
     */
    public AcademicProfessionalDTO(int professionalId, String name, String currentInstitution, String academicPosition, String educationBackground, String areaOfExpertise) {
        this.professionalId = professionalId;
        this.name = name;
        this.currentInstitution = currentInstitution;
        this.academicPosition = academicPosition;
        this.educationBackground = educationBackground;
        this.areaOfExpertise = areaOfExpertise;
    }

    /**
     * Gets the professional's unique identifier.
     * @return The professional ID
     */
    public int getProfessionalId() { return professionalId; }

    /**
     * Sets the professional's unique identifier.
     * @param professionalId The professional ID to set
     */
    public void setProfessionalId(int professionalId) { this.professionalId = professionalId; }

    /**
     * Gets the professional's full name.
     * @return The professional's name
     */
    public String getName() { return name; }

    /**
     * Sets the professional's full name.
     * @param name The name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the professional's current institution.
     * @return The current institution name
     */
    public String getCurrentInstitution() { return currentInstitution; }

    /**
     * Sets the professional's current institution.
     * @param currentInstitution The institution name to set
     */
    public void setCurrentInstitution(String currentInstitution) { this.currentInstitution = currentInstitution; }

    /**
     * Gets the professional's academic position.
     * @return The academic position
     */
    public String getAcademicPosition() { return academicPosition; }

    /**
     * Sets the professional's academic position.
     * @param academicPosition The academic position to set
     */
    public void setAcademicPosition(String academicPosition) { this.academicPosition = academicPosition; }

    /**
     * Gets the professional's educational background.
     * @return The educational background
     */
    public String getEducationBackground() { return educationBackground; }

    /**
     * Sets the professional's educational background.
     * @param educationBackground The educational background to set
     */
    public void setEducationBackground(String educationBackground) { this.educationBackground = educationBackground; }

    /**
     * Gets the professional's areas of expertise.
     * @return The areas of expertise
     */
    public String getAreaOfExpertise() { return areaOfExpertise; }

    /**
     * Sets the professional's areas of expertise.
     * @param areaOfExpertise The areas of expertise to set
     */
    public void setAreaOfExpertise(String areaOfExpertise) { this.areaOfExpertise = areaOfExpertise; }
}
