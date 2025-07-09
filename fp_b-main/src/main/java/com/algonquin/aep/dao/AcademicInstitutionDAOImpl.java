package com.algonquin.aep.dao;

import com.algonquin.aep.dto.AcademicInstitutionDTO;
import com.algonquin.aep.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Implementation of the AcademicInstitutionDAO interface.
 * This class provides concrete implementations for managing academic institution data
 * in the database, including CRUD operations and specific queries.
 */
public class AcademicInstitutionDAOImpl implements AcademicInstitutionDAO {
    /** SQL query to insert a new institution */
    private static final String INSERT_INSTITUTION = "INSERT INTO academic_institutions (institution_id, name, address) VALUES (?, ?, ?)";
    
    /** SQL query to find an institution by ID */
    private static final String FIND_BY_ID = "SELECT * FROM academic_institutions WHERE institution_id = ?";
    
    /** SQL query to update an institution */
    private static final String UPDATE_INSTITUTION = "UPDATE academic_institutions SET name = ?, address = ? WHERE institution_id = ?";
    
    /** SQL query to delete an institution */
    private static final String DELETE_INSTITUTION = "DELETE FROM academic_institutions WHERE institution_id = ?";
    
    /** SQL query to get an institution's name by ID */
    private static final String GET_NAME_BY_ID = "SELECT name FROM academic_institutions WHERE institution_id = ?";

    private Connection connection;

    /**
     * Constructs a new AcademicInstitutionDAOImpl instance.
     * Initializes the database connection using DBConnection singleton.
     */
    public AcademicInstitutionDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL INSERT statement to create a new academic institution record.
     * 
     * @param institution The institution data to be inserted
     * @throws RuntimeException if a database error occurs
     */
    @Override
    public void insertInstitution(AcademicInstitutionDTO institution) {
        try {
            PreparedStatement stmt = connection.prepareStatement(INSERT_INSTITUTION);
            stmt.setInt(1, institution.getInstitutionId());
            stmt.setString(2, institution.getName());
            stmt.setString(3, institution.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting institution", e);
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL SELECT statement to find an academic institution by ID.
     * 
     * @param institutionId The ID of the institution to find
     * @return The found institution data, or null if not found
     * @throws RuntimeException if a database error occurs
     */
    @Override
    public AcademicInstitutionDTO findInstitutionById(int institutionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, institutionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToInstitution(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding institution by ID", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * Executes an SQL UPDATE statement to modify an existing institution record.
     * 
     * @param institution The updated institution data
     * @throws RuntimeException if a database error occurs
     */
    @Override
    public void updateInstitution(AcademicInstitutionDTO institution) {
        try {
            PreparedStatement stmt = connection.prepareStatement(UPDATE_INSTITUTION);
            stmt.setString(1, institution.getName());
            stmt.setString(2, institution.getAddress());
            stmt.setInt(3, institution.getInstitutionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating institution", e);
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL DELETE statement to remove an institution record.
     * 
     * @param institutionId The ID of the institution to delete
     * @throws RuntimeException if a database error occurs
     */
    @Override
    public void deleteInstitution(int institutionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DELETE_INSTITUTION);
            stmt.setInt(1, institutionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting institution", e);
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL SELECT statement to retrieve an institution's name by ID.
     * 
     * @param institutionId The ID of the institution
     * @return The name of the institution, or null if not found
     * @throws RuntimeException if a database error occurs
     */
    @Override
    public String getInstitutionNameById(int institutionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(GET_NAME_BY_ID);
            stmt.setInt(1, institutionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting institution name by ID", e);
        }
        return null;
    }

    /**
     * Maps a ResultSet row to an AcademicInstitutionDTO object.
     * 
     * @param rs The ResultSet containing the institution data
     * @return A populated AcademicInstitutionDTO object
     * @throws SQLException if a database access error occurs
     */
    private AcademicInstitutionDTO mapRowToInstitution(ResultSet rs) throws SQLException {
        AcademicInstitutionDTO institution = new AcademicInstitutionDTO();
        institution.setInstitutionId(rs.getInt("institution_id"));
        institution.setName(rs.getString("name"));
        institution.setAddress(rs.getString("address"));
        return institution;
    }
}
