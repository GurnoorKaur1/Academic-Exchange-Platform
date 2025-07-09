package com.algonquin.aep.dao;

import com.algonquin.aep.dto.AcademicProfessionalDTO;

/**
 * Data Access Object interface for managing Academic Professional data.
 * Provides methods to handle academic professional-related operations in the system,
 * including profile management, qualifications, and teaching preferences.
 */
public interface AcademicProfessionalDAO {
    /**
     * Inserts a new academic professional record into the database.
     * 
     * @param professional The professional data to be inserted
     */
    void insertProfessional(AcademicProfessionalDTO professional);

    /**
     * Retrieves an academic professional by their unique identifier.
     * 
     * @param professionalId The ID of the professional to find
     * @return The professional data, or null if not found
     */
    AcademicProfessionalDTO findProfessionalById(int professionalId);

    /**
     * Updates an existing academic professional's record in the database.
     * 
     * @param professional The updated professional data
     * @return true if the update was successful, false otherwise
     */
    boolean updateProfessional(AcademicProfessionalDTO professional);

    /**
     * Deletes an academic professional's record from the database.
     * 
     * @param professionalId The ID of the professional to delete
     */
    void deleteProfessional(int professionalId);

    /**
     * Retrieves an academic professional's name by their unique identifier.
     * 
     * @param professionalId The ID of the professional to find
     * @return The professional's name, or null if not found
     */
    String getProfessionalNameById(int professionalId);
    // AcademicProfessionalDTO getProfessionalById(int professionalId);
}
