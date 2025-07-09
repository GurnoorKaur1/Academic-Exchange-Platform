package com.algonquin.aep.dao;

import com.algonquin.aep.dto.AcademicInstitutionDTO;

/**
 * Data Access Object interface for managing Academic Institution data.
 * Provides methods to perform CRUD operations and queries on academic institution records
 * in the database.
 */
public interface AcademicInstitutionDAO {
    /**
     * Inserts a new academic institution record into the database.
     * 
     * @param institution The institution data to be inserted
     */
    void insertInstitution(AcademicInstitutionDTO institution);

    /**
     * Retrieves an academic institution by its unique identifier.
     * 
     * @param institutionId The ID of the institution to find
     * @return The found institution data, or null if not found
     */
    AcademicInstitutionDTO findInstitutionById(int institutionId);

    /**
     * Updates an existing academic institution record in the database.
     * 
     * @param institution The updated institution data
     */
    void updateInstitution(AcademicInstitutionDTO institution);

    /**
     * Deletes an academic institution record from the database by its unique identifier.
     * 
     * @param institutionId The ID of the institution to delete
     */
    void deleteInstitution(int institutionId);

    /**
     * Retrieves the name of an academic institution by its unique identifier.
     * 
     * @param institutionId The ID of the institution to find
     * @return The name of the institution, or null if not found
     */
    String getInstitutionNameById(int institutionId);
}
