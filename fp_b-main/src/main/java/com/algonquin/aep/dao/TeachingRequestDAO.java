package com.algonquin.aep.dao;

import com.algonquin.aep.dto.TeachingRequestDTO;
import java.util.List;

/**
 * Data Access Object interface for managing Teaching Request data.
 * Provides methods to handle teaching requests in the system, including creating,
 * retrieving, and managing requests from academic professionals to teach courses.
 */
public interface TeachingRequestDAO {
    /**
     * Creates a new teaching request in the database.
     * 
     * @param request The teaching request data to be created
     * @return True if the request is created successfully, false otherwise
     */
    boolean create(TeachingRequestDTO request);

    /**
     * Updates the status of a teaching request.
     * 
     * @param request The teaching request data to be updated
     * @return True if the request is updated successfully, false otherwise
     */
    boolean update(TeachingRequestDTO request);

    /**
     * Retrieves a teaching request by its ID.
     * 
     * @param requestId The ID of the teaching request
     * @return The teaching request data
     */
    TeachingRequestDTO findById(Integer requestId);

    /**
     * Retrieves all teaching requests for a specific professional.
     * 
     * @param professionalId The ID of the academic professional
     * @return List of teaching requests made by the professional
     */
    List<TeachingRequestDTO> findByProfessionalId(Integer professionalId);

    /**
     * Retrieves all teaching requests for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of teaching requests for the institution
     */
    List<TeachingRequestDTO> findByInstitutionId(Integer institutionId);
}
