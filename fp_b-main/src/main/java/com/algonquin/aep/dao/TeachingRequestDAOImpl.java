package com.algonquin.aep.dao;

import com.algonquin.aep.dto.TeachingRequestDTO;
import com.algonquin.aep.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of the TeachingRequestDAO interface.
 * This class handles all database operations related to teaching requests,
 * including creation, retrieval, updates, and management of teaching request statuses.
 * It provides functionality for both applicants and administrators to interact with
 * teaching request data.
 */
public class TeachingRequestDAOImpl implements TeachingRequestDAO {
    private Connection connection;

    /**
     * Constructs a new TeachingRequestDAOImpl instance.
     * Initializes the database connection using DBConnection singleton.
     */
    public TeachingRequestDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new teaching request in the database.
     * 
     * @param request The TeachingRequestDTO containing request details
     * @return true if the request is created successfully, false otherwise
     */
    @Override
    public boolean create(TeachingRequestDTO request) {
        try {
            String sql = "INSERT INTO teaching_requests (professional_id, course_id, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setInt(1, request.getProfessionalId());
            stmt.setInt(2, request.getCourseId());
            stmt.setString(3, request.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    request.setRequestId(generatedKeys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the status of an existing teaching request.
     * 
     * @param request The TeachingRequestDTO containing request details
     * @return true if the request is updated successfully, false otherwise
     */
    @Override
    public boolean update(TeachingRequestDTO request) {
        try {
            String sql = "UPDATE teaching_requests SET status = ? WHERE request_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, request.getStatus());
            stmt.setInt(2, request.getRequestId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a teaching request by its ID.
     * 
     * @param requestId The ID of the request to retrieve
     * @return The TeachingRequestDTO object if found, null otherwise
     */
    @Override
    public TeachingRequestDTO findById(Integer requestId) {
        try {
            String sql = "SELECT * FROM teaching_requests WHERE request_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, requestId);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all teaching requests submitted by a specific professional.
     * 
     * @param professionalId The ID of the professional
     * @return List of TeachingRequestDTO objects, empty list if none found
     */
    @Override
    public List<TeachingRequestDTO> findByProfessionalId(Integer professionalId) {
        List<TeachingRequestDTO> requests = new ArrayList<>();
        try {
            String sql = "SELECT * FROM teaching_requests WHERE professional_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, professionalId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    /**
     * Retrieves all teaching requests for a specific institution.
     * 
     * @param institutionId The ID of the institution
     * @return List of TeachingRequestDTO objects, empty list if none found
     */
    @Override
    public List<TeachingRequestDTO> findByInstitutionId(Integer institutionId) {
        List<TeachingRequestDTO> requests = new ArrayList<>();
        try {
            String sql = "SELECT tr.*, c.code as course_code, c.title as course_title, " +
                        "ap.name as professional_name " +
                        "FROM teaching_requests tr " +
                        "JOIN courses c ON tr.course_id = c.course_id " +
                        "JOIN academic_professionals ap ON tr.professional_id = ap.professional_id " +
                        "WHERE c.institution_id = ?";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, institutionId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(mapResultSetToDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    /**
     * Maps a ResultSet row to a TeachingRequestDTO object.
     * 
     * @param rs The ResultSet containing request data
     * @return A populated TeachingRequestDTO object
     * @throws SQLException if a database access error occurs
     */
    private TeachingRequestDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        TeachingRequestDTO request = new TeachingRequestDTO();
        request.setRequestId(rs.getInt("request_id"));
        request.setProfessionalId(rs.getInt("professional_id"));
        request.setCourseId(rs.getInt("course_id"));
        request.setStatus(rs.getString("status"));
        request.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Map additional fields
        try {
            request.setCourseCode(rs.getString("course_code"));
            request.setCourseTitle(rs.getString("course_title"));
            request.setProfessionalName(rs.getString("professional_name"));
        } catch (SQLException e) {
            // These fields might not be present in all queries
        }
        
        return request;
    }
}
