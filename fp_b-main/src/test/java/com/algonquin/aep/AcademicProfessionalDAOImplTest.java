package com.algonquin.aep;

import com.algonquin.aep.dao.AcademicProfessionalDAO;
import com.algonquin.aep.dao.AcademicProfessionalDAOImpl;
import com.algonquin.aep.dto.AcademicProfessionalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AcademicProfessionalDAOImpl class.
 * This class tests the CRUD operations for AcademicProfessional entities.
 */
class AcademicProfessionalDAOImplTest {

    private AcademicProfessionalDAO professionalDAO;

    /**
     * Sets up the test environment before each test.
     * Initializes the AcademicProfessionalDAO implementation.
     */
    @BeforeEach
    void setUp() {
        professionalDAO = new AcademicProfessionalDAOImpl();
    }

    /**
     * Tests the insertion of a new AcademicProfessional entity.
     * Verifies that the entity can be retrieved by its ID and matches the expected values.
     *
     */
    @Test
    void testInsertProfessional() {
        AcademicProfessionalDTO professional = new AcademicProfessionalDTO();
        professional.setProfessionalId(7);
        professional.setName("Michael Johnson");

        professional.setCurrentInstitution("University of Toronto");
        professional.setAcademicPosition("Professor");
        professional.setEducationBackground("PhD in Computer Science");
        professional.setAreaOfExpertise("Artificial Intelligence");

        professionalDAO.insertProfessional(professional);
        AcademicProfessionalDTO retrievedProfessional = professionalDAO.findProfessionalById(7);

        assertNotNull(retrievedProfessional);
        assertEquals("Michael Johnson", retrievedProfessional.getName());
        assertEquals("University of Toronto", retrievedProfessional.getCurrentInstitution());
    }

    /**
     * Tests the update operation for an existing AcademicProfessional entity.
     * Verifies that the update is successful and the entity's details are changed accordingly.
     */
    @Test
    void testUpdateProfessional() {
        AcademicProfessionalDTO professional = new AcademicProfessionalDTO();
        professional.setProfessionalId(8);
        professional.setName("William Smith");    
        professional.setCurrentInstitution("McGill University");
        professional.setAcademicPosition("Associate Professor");
        professional.setEducationBackground("PhD in Mathematics");
        professional.setAreaOfExpertise("Data Science");

        professionalDAO.insertProfessional(professional);

        // Update details
        professional.setCurrentInstitution("Harvard University");
        boolean success = professionalDAO.updateProfessional(professional);

        assertTrue(success);
        AcademicProfessionalDTO updatedProfessional = professionalDAO.findProfessionalById(8);
        assertEquals("Harvard University", updatedProfessional.getCurrentInstitution());
    }
}
