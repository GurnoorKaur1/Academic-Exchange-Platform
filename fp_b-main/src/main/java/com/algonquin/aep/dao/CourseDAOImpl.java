package com.algonquin.aep.dao;

import com.algonquin.aep.dto.CourseDTO;
import com.algonquin.aep.dto.CourseSearchDTO;
import com.algonquin.aep.dto.InstitutionDTO;
import com.algonquin.aep.util.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the CourseDAO interface.
 * This class handles all database operations related to courses, including CRUD operations
 * and specific queries for course management. It uses JDBC for database interactions.
 */
public class CourseDAOImpl implements CourseDAO {
    private static final Logger log = LogManager.getLogger(CourseDAOImpl.class);
    private Connection connection;

    private static final String BASE_SEARCH_QUERY = 
        "SELECT c.*, i.name as institution_name " +
        "FROM courses c " +
        "JOIN academic_institutions i ON c.institution_id = i.institution_id " +
        "WHERE 1=1 ";

    /**
     * Constructs a new CourseDAOImpl instance.
     * Initializes the database connection using DBConnection singleton.
     */
    public CourseDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
            log.info("CourseDAOImpl initialized with database connection");
            if (this.connection != null && !this.connection.isClosed()) {
                log.info("Database connection is valid");
            } else {
                log.error("Database connection is not valid");
            }
        } catch (SQLException e) {
            log.error("Error initializing CourseDAOImpl", e);
        }
    }

    /**
     * Inserts a new course record into the database.
     * 
     * @param course The CourseDTO object containing course information
     */
    @Override
    public void insertCourse(CourseDTO course) {
        String sql = "INSERT INTO courses (institution_id, title, code, term, outline, schedule, preferred_qualifications, delivery_method, compensation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, course.getInstitutionId());
            stmt.setString(2, course.getTitle());
            stmt.setString(3, course.getCode());
            stmt.setString(4, course.getTerm());
            stmt.setString(5, course.getOutline());
            stmt.setString(6, course.getSchedule());
            stmt.setString(7, course.getPreferredQualifications());
            stmt.setString(8, course.getDeliveryMethod());
            stmt.setDouble(9, course.getCompensation().doubleValue());
            stmt.executeUpdate();
            log.info("Course inserted successfully: {}", course.getCode());
        } catch (SQLException e) {
            log.error("Error inserting course: {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves a course from the database by its ID.
     * 
     * @param courseId The ID of the course to retrieve
     * @return CourseDTO object if found, null otherwise
     */
    @Override
    public CourseDTO findCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        CourseDTO course = new CourseDTO();
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                course.setCourseId(rs.getInt("course_id"));
                course.setInstitutionId(rs.getInt("institution_id"));
                course.setTitle(rs.getString("title"));
                course.setCode(rs.getString("code"));
                course.setTerm(rs.getString("term"));
                course.setOutline(rs.getString("outline"));
                course.setSchedule(rs.getString("schedule"));
                course.setPreferredQualifications(rs.getString("preferred_qualifications"));
                course.setDeliveryMethod(rs.getString("delivery_method"));
                course.setCompensation(rs.getDouble("compensation"));
            }
        } catch (SQLException e) {
            log.error("Error finding course by ID: {}", e.getMessage(), e);
        }
        return course;
    }

    /**
     * Retrieves all courses associated with a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of CourseDTO objects, empty list if none found
     */
    @Override
    public List<CourseDTO> getCoursesByInstitutionId(int institutionId) {
        List<CourseDTO> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE institution_id = ?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, institutionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CourseDTO course = new CourseDTO();
                course.setCourseId(rs.getInt("course_id"));
                course.setInstitutionId(rs.getInt("institution_id"));
                course.setTitle(rs.getString("title"));
                course.setCode(rs.getString("code"));
                course.setTerm(rs.getString("term"));
                course.setOutline(rs.getString("outline"));
                course.setSchedule(rs.getString("schedule"));
                course.setPreferredQualifications(rs.getString("preferred_qualifications"));
                course.setDeliveryMethod(rs.getString("delivery_method"));
                course.setCompensation(rs.getDouble("compensation"));
                courses.add(course);
            }
        } catch (SQLException e) {
            log.error("Error getting courses by institution ID: {}", e.getMessage(), e);
        }
        return courses;
    }

    /**
     * Updates an existing course record in the database.
     * 
     * @param course The CourseDTO object containing updated course information
     */
    @Override
    public void updateCourse(CourseDTO course) {
        String sql = "UPDATE courses SET title=?, code=?, term=?, outline=?, schedule=?, preferred_qualifications=?, delivery_method=?, compensation=? WHERE course_id=?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getCode());
            stmt.setString(3, course.getTerm());
            stmt.setString(4, course.getOutline());
            stmt.setString(5, course.getSchedule());
            stmt.setString(6, course.getPreferredQualifications());
            stmt.setString(7, course.getDeliveryMethod());
            stmt.setDouble(8, course.getCompensation().doubleValue());
            stmt.setInt(9, course.getCourseId());
            stmt.executeUpdate();
            log.info("Course updated successfully: {}", course.getCourseId());
        } catch (SQLException e) {
            log.error("Error updating course: {}", e.getMessage(), e);
        }
    }

    /**
     * Deletes a course record from the database.
     * 
     * @param courseId The ID of the course to delete
     */
    @Override
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            stmt.executeUpdate();
            log.info("Course deleted successfully: {}", courseId);
        } catch (SQLException e) {
            log.error("Error deleting course: {}", e.getMessage(), e);
        }
    }

    /**
     * Searches for courses based on the provided search criteria.
     * 
     * @param searchDTO The CourseSearchDTO object containing search criteria
     * @return List of CourseDTO objects matching the search criteria, empty list if none found
     */
    @Override
    public List<CourseDTO> searchCourses(CourseSearchDTO searchDTO) {
        List<CourseDTO> results = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        
        String sql = buildSearchQuery(searchDTO, params);
        log.info("Executing search query with {} parameters", params.size());
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setQueryParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToCourseDTO(rs));
                }
            }
            log.info("Found {} matching courses", results.size());
        } catch (SQLException e) {
            log.error("Error searching courses: {}", e.getMessage(), e);
        }
        
        return results;
    }

    /**
     * Builds the SQL query for searching courses based on the provided search criteria.
     * 
     * @param searchDTO The CourseSearchDTO object containing search criteria
     * @param params List of parameters to be used in the query
     * @return The built SQL query
     */
    private String buildSearchQuery(CourseSearchDTO searchDTO, List<Object> params) {
        StringBuilder sql = new StringBuilder(BASE_SEARCH_QUERY);
        
        addConditionIfPresent(sql, "i.name = ?", searchDTO.getInstitutionName(), params);
        addConditionIfPresent(sql, "c.code = ?", searchDTO.getCourseCode(), params);
        addConditionIfPresent(sql, "c.term = ?", searchDTO.getTerm(), params);
        addConditionIfPresent(sql, "c.schedule LIKE ?", searchDTO.getSchedule(), params, true);
        addConditionIfPresent(sql, "c.delivery_method = ?", searchDTO.getDeliveryMethod(), params);
        
        sql.append("ORDER BY i.name, c.code");
        return sql.toString();
    }

    /**
     * Adds a condition to the SQL query if the provided value is not null or empty.
     * 
     * @param sql The StringBuilder containing the SQL query
     * @param condition The condition to be added
     * @param value The value to be used in the condition
     * @param params List of parameters to be used in the query
     */
    private void addConditionIfPresent(StringBuilder sql, String condition, String value, List<Object> params) {
        addConditionIfPresent(sql, condition, value, params, false);
    }

    /**
     * Adds a condition to the SQL query if the provided value is not null or empty.
     * 
     * @param sql The StringBuilder containing the SQL query
     * @param condition The condition to be added
     * @param value The value to be used in the condition
     * @param params List of parameters to be used in the query
     * @param isLike Whether the condition is a LIKE clause
     */
    private void addConditionIfPresent(StringBuilder sql, String condition, String value, List<Object> params, boolean isLike) {
        if (value != null && !value.isEmpty()) {
            sql.append("AND ").append(condition).append(" ");
            params.add(isLike ? "%" + value + "%" : value);
        }
    }

    /**
     * Sets the parameters for the prepared statement.
     * 
     * @param stmt The PreparedStatement to be used
     * @param params List of parameters to be used in the query
     * @throws SQLException if a database access error occurs
     */
    private void setQueryParameters(PreparedStatement stmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }
    }

    /**
     * Maps a ResultSet row to a CourseDTO object.
     * 
     * @param rs The ResultSet containing course data
     * @return A populated CourseDTO object
     * @throws SQLException if a database access error occurs
     */
    private CourseDTO mapResultSetToCourseDTO(ResultSet rs) throws SQLException {
        CourseDTO course = new CourseDTO();
        course.setCourseId(rs.getInt("course_id"));
        course.setInstitutionId(rs.getInt("institution_id"));
        course.setTitle(rs.getString("title"));
        course.setCode(rs.getString("code"));
        course.setTerm(rs.getString("term"));
        course.setSchedule(rs.getString("schedule"));
        course.setDeliveryMethod(rs.getString("delivery_method"));
        course.setInstitutionName(rs.getString("institution_name"));
        return course;
    }

    /**
     * Retrieves all institutions from the database.
     * 
     * @return List of InstitutionDTO objects, empty list if none found
     */
    @Override
    public List<InstitutionDTO> getAllInstitutions() {
        List<InstitutionDTO> institutions = new ArrayList<>();
        String sql = "SELECT institution_id, name FROM academic_institutions ORDER BY name";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InstitutionDTO institution = new InstitutionDTO();
                institution.setId(rs.getInt("institution_id"));
                institution.setName(rs.getString("name"));
                institutions.add(institution);
            }
            log.info("Found {} institutions", institutions.size());
        } catch (SQLException e) {
            log.error("Error fetching institutions: {}", e.getMessage(), e);
        }
        return institutions;
    }

    /**
     * Retrieves all course codes for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of course codes, empty list if none found
     */
    @Override
    public List<String> getCourseCodesByInstitution(String institutionId) {
        List<String> courseCodes = new ArrayList<>();
        String sql = "SELECT DISTINCT code FROM courses WHERE institution_id = ? ORDER BY code";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, institutionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                courseCodes.add(rs.getString("code"));
            }
            log.info("Found {} course codes for institution {}", courseCodes.size(), institutionId);
        } catch (SQLException e) {
            log.error("Error fetching course codes: {}", e.getMessage(), e);
        }
        return courseCodes;
    }

    /**
     * Retrieves all course titles for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of course titles, empty list if none found
     */
    @Override
    public List<String> getCourseTitlesByInstitution(String institutionId) {
        List<String> titles = new ArrayList<>();
        String sql = "SELECT DISTINCT title FROM courses WHERE institution_id = ? ORDER BY title";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, institutionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
            log.info("Found {} course titles for institution {}", titles.size(), institutionId);
        } catch (SQLException e) {
            log.error("Error fetching course titles: {}", e.getMessage(), e);
        }
        return titles;
    }

    /**
     * Retrieves all terms from the database.
     * 
     * @return List of terms, empty list if none found
     */
    @Override
    public List<String> getAllTerms() {
        List<String> terms = new ArrayList<>();
        String sql = "SELECT DISTINCT term FROM courses ORDER BY term";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                terms.add(rs.getString("term"));
            }
            log.info("Found {} terms", terms.size());
        } catch (SQLException e) {
            log.error("Error fetching terms: {}", e.getMessage(), e);
        }
        return terms;
    }

    /**
     * Retrieves the title of a course by its code and institution ID.
     * 
     * @param institutionId The ID of the institution
     * @param courseCode The code of the course
     * @return The title of the course, null if not found
     */
    @Override
    public String getCourseTitleByCode(String institutionId, String courseCode) {
        String title = null;
        String sql = "SELECT title FROM courses WHERE institution_id = ? AND code = ?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, institutionId);
            stmt.setString(2, courseCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                title = rs.getString("title");
                log.debug("Found title: {} for code: {}", title, courseCode);
            }
        } catch (SQLException e) {
            log.error("Error fetching course title: {}", e.getMessage(), e);
        }
        return title;
    }

    /**
     * Retrieves all terms for a specific course code and institution ID.
     * 
     * @param institutionId The ID of the institution
     * @param courseCode The code of the course
     * @return List of terms, empty list if none found
     */
    @Override
    public List<String> getTermsByCourseCode(String institutionId, String courseCode) {
        List<String> terms = new ArrayList<>();
        String sql = "SELECT DISTINCT term FROM courses WHERE institution_id = ? AND code = ? ORDER BY term";
        
        try {
            if (connection == null || connection.isClosed()) {
                connection = DBConnection.getInstance().getConnection();
            }
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, institutionId);
                stmt.setString(2, courseCode);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        terms.add(rs.getString("term"));
                    }
                }
            }
            log.debug("Found {} terms for course code: {}", terms.size(), courseCode);
            
        } catch (SQLException e) {
            log.error("Error fetching terms: {}", e.getMessage(), e);
        }
        
        return terms;
    }
}
