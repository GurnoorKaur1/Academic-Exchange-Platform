package com.algonquin.aep.dto;

/**
 * DTO representing an Academic Institution.
 * This class is used to transfer academic institution data between different layers of the application.
 */
public class AcademicInstitutionDTO {
    private int institutionId;
    private String name;
    private String address;

    /**
     * Default constructor for AcademicInstitutionDTO.
     */
    public AcademicInstitutionDTO() {}

    /**
     * Constructs a new AcademicInstitutionDTO with specified parameters.
     *
     * @param institutionId The unique identifier of the academic institution
     * @param name The name of the academic institution
     * @param address The physical address of the academic institution
     */
    public AcademicInstitutionDTO(int institutionId, String name, String address) {
        this.institutionId = institutionId;
        this.name = name;
        this.address = address;
    }

    /**
     * Gets the institution ID.
     *
     * @return The unique identifier of the academic institution
     */
    public int getInstitutionId() {
        return institutionId;
    }

    /**
     * Sets the institution ID.
     *
     * @param institutionId The unique identifier to set for the academic institution
     */
    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    /**
     * Gets the name of the academic institution.
     *
     * @return The name of the academic institution
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the academic institution.
     *
     * @param name The name to set for the academic institution
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the academic institution.
     *
     * @return The physical address of the academic institution
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the academic institution.
     *
     * @param address The address to set for the academic institution
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
