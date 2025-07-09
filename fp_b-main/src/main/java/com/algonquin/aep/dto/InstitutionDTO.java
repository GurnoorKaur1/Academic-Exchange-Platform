package com.algonquin.aep.dto;

/**
 * Data Transfer Object representing an Institution.
 * This class is used to transfer basic institution data between different layers of the application.
 */
public class InstitutionDTO {
    private int id;
    private String name;

    /**
     * Default constructor for InstitutionDTO.
     */
    public InstitutionDTO() {
    }
    
    /**
     * Constructs a new InstitutionDTO with specified parameters.
     *
     * @param id The unique identifier of the institution
     * @param name The name of the institution
     */
    public InstitutionDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the institution ID.
     *
     * @return The unique identifier of the institution
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the institution ID.
     *
     * @param id The unique identifier to set for the institution
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the name of the institution.
     *
     * @return The name of the institution
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the institution.
     *
     * @param name The name to set for the institution
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns a string representation of the InstitutionDTO.
     *
     * @return A string containing the institution's ID and name
     */
    @Override
    public String toString() {
        return "InstitutionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}