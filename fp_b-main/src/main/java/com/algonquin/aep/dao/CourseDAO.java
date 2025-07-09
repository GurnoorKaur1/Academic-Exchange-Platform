package com.algonquin.aep.dao;

import com.algonquin.aep.dto.CourseDTO;
import com.algonquin.aep.dto.CourseSearchDTO;
import com.algonquin.aep.dto.InstitutionDTO;

import java.util.List;

/**
 * Data Access Object interface for managing Course data.
 * Provides methods to perform CRUD operations, search functionality, and various queries
 * on course records in the database. This interface supports both course management for
 * institutions and course search for academic professionals.
 */
public interface CourseDAO {
    /**
     * Inserts a new course record into the database.
     * 
     * @param course The course data to be inserted
     */
    void insertCourse(CourseDTO course);

    /**
     * Retrieves a course by its unique identifier.
     * 
     * @param courseId The ID of the course to find
     * @return The found course data, or null if not found
     */
    CourseDTO findCourseById(int courseId);

    /**
     * Retrieves all courses offered by a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of courses offered by the institution
     */
    List<CourseDTO> getCoursesByInstitutionId(int institutionId);

    /**
     * Updates an existing course record in the database.
     * 
     * @param course The updated course data
     */
    void updateCourse(CourseDTO course);

    /**
     * Deletes a course record from the database.
     * 
     * @param courseId The ID of the course to delete
     */
    void deleteCourse(int courseId);

    /**
     * Searches for courses based on the provided search criteria.
     * 
     * @param searchDTO The search criteria containing various filter options
     * @return List of courses matching the search criteria
     */
    List<CourseDTO> searchCourses(CourseSearchDTO searchDTO);

    /**
     * Retrieves all available institutions.
     * 
     * @return List of institutions
     */
    List<InstitutionDTO> getAllInstitutions();

    /**
     * Retrieves all available course codes for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of unique course codes
     */
    List<String> getCourseCodesByInstitution(String institutionId);

    /**
     * Retrieves all available course titles for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of unique course titles
     */
    List<String> getCourseTitlesByInstitution(String institutionId);

    /**
     * Retrieves all available terms.
     * 
     * @return List of available terms
     */
    List<String> getAllTerms();

    /**
     * Retrieves the title of a specific course.
     * 
     * @param institutionId The ID of the institution
     * @param courseCode The course code
     * @return The course title, or null if not found
     */
    String getCourseTitleByCode(String institutionId, String courseCode);

    /**
     * Retrieves all available terms for a specific course at an institution.
     * 
     * @param institutionId The ID of the institution
     * @param courseCode The course code
     * @return List of available terms
     */
    List<String> getTermsByCourseCode(String institutionId, String courseCode);
}
