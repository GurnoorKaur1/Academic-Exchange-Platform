package com.algonquin.aep.dao;

import com.algonquin.aep.dto.AcademicProfessionalDTO;
import com.algonquin.aep.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the AcademicProfessionalDAO interface.
 * This class provides concrete implementations for managing academic professional data
 * in the database, including CRUD operations and specific queries.
 */
public class AcademicProfessionalDAOImpl implements AcademicProfessionalDAO {
    /** SQL query to insert a new academic professional */
    private static final String INSERT_PROFESSIONAL = "INSERT INTO academic_professionals (professional_id, name, current_institution, academic_position, education_background, area_of_expertise) VALUES (?, ?, ?, ?, ?, ?)";
    
    /** SQL query to find a professional by ID */
    private static final String FIND_BY_ID = "SELECT * FROM academic_professionals WHERE professional_id = ?";
    
    /** SQL query to update a professional's record */
    private static final String UPDATE_PROFESSIONAL = "UPDATE academic_professionals SET name = ?, current_institution = ?, academic_position = ?, education_background = ?, area_of_expertise = ? WHERE professional_id = ?";
    
    /** SQL query to delete a professional's record */
    private static final String DELETE_PROFESSIONAL = "DELETE FROM academic_professionals WHERE professional_id = ?";
    
    /** SQL query to get a professional's name by ID */
    private static final String GET_NAME_BY_ID = "SELECT name FROM academic_professionals WHERE professional_id = ?";

    private Connection connection;

    /**
     * Constructs a new AcademicProfessionalDAOImpl instance.
     * Initializes the database connection.
     */
    public AcademicProfessionalDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL INSERT statement to create a new academic professional record.
     * 
     * @param professional The AcademicProfessionalDTO object to be inserted
     */
    @Override
    public void insertProfessional(AcademicProfessionalDTO professional) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(INSERT_PROFESSIONAL);

            stmt.setInt(1, professional.getProfessionalId());
            stmt.setString(2, professional.getName());
            stmt.setString(3, professional.getCurrentInstitution());
            stmt.setString(4, professional.getAcademicPosition());
            stmt.setString(5, professional.getEducationBackground());
            stmt.setString(6, professional.getAreaOfExpertise());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL SELECT statement to find an academic professional by their ID.
     * 
     * @param professionalId The ID of the professional to be found
     * @return The AcademicProfessionalDTO object representing the found professional, or null if not found
     */
    @Override
    public AcademicProfessionalDTO findProfessionalById(int professionalId) {
        AcademicProfessionalDTO professional = null;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, professionalId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                professional = new AcademicProfessionalDTO();
                professional.setProfessionalId(rs.getInt("professional_id"));
                professional.setName(rs.getString("name"));
                professional.setCurrentInstitution(rs.getString("current_institution"));
                professional.setAcademicPosition(rs.getString("academic_position"));
                professional.setEducationBackground(rs.getString("education_background"));
                professional.setAreaOfExpertise(rs.getString("area_of_expertise"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return professional;
    }

    /**
     * {@inheritDoc}
     * Executes an SQL UPDATE statement to modify an existing academic professional record.
     * 
     * @param professional The AcademicProfessionalDTO object to be updated
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateProfessional(AcademicProfessionalDTO professional) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(UPDATE_PROFESSIONAL);

            stmt.setString(1, professional.getName());
            stmt.setString(2, professional.getCurrentInstitution());
            stmt.setString(3, professional.getAcademicPosition());
            stmt.setString(4, professional.getEducationBackground());
            stmt.setString(5, professional.getAreaOfExpertise());
            stmt.setInt(6, professional.getProfessionalId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL DELETE statement to remove an academic professional record.
     * 
     * @param professionalId The ID of the professional to be deleted
     */
    @Override
    public void deleteProfessional(int professionalId) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(DELETE_PROFESSIONAL);
            stmt.setInt(1, professionalId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     * Executes an SQL SELECT statement to retrieve a professional's name by their ID.
     * 
     * @param professionalId The ID of the professional
     * @return The name of the professional, or null if not found
     */
    @Override
    public String getProfessionalNameById(int professionalId) {
        String name = null;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(GET_NAME_BY_ID);
            stmt.setInt(1, professionalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}
