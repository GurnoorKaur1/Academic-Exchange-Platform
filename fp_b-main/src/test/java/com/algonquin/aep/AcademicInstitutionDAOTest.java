package com.algonquin.aep;

import com.algonquin.aep.dao.AcademicInstitutionDAO;
import com.algonquin.aep.dao.AcademicInstitutionDAOImpl;
import com.algonquin.aep.dto.AcademicInstitutionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AcademicInstitutionDAO class.
 * This class tests the CRUD operations for AcademicInstitution entities.
 */
class AcademicInstitutionDAOTest {

    private AcademicInstitutionDAO institutionDAO;

    @BeforeEach
    void setUp() {
        institutionDAO = new AcademicInstitutionDAOImpl();
    }

    /**
     * Tests the insertion of a new AcademicInstitution entity.
     * Verifies that the entity can be retrieved by its ID and matches the expected values.
     */
    @Test
    void testInsertInstitution() {
        AcademicInstitutionDTO institution = new AcademicInstitutionDTO();
        institution.setInstitutionId(6);
        institution.setName("University of Waterloo");

        institutionDAO.insertInstitution(institution);
        AcademicInstitutionDTO retrievedInstitution = institutionDAO.findInstitutionById(6);

        assertNotNull(retrievedInstitution);
        assertEquals("University of Waterloo", retrievedInstitution.getName());
    }

    /**
     * Tests the retrieval of an AcademicInstitution entity by its ID.
     * Verifies that the retrieved entity matches the expected values.
     */
    @Test
    void testFindInstitutionById() {
        AcademicInstitutionDTO institution = new AcademicInstitutionDTO();
        institution.setInstitutionId(6);
        institution.setName("University of Waterloo");

        AcademicInstitutionDTO retrievedInstitution = institutionDAO.findInstitutionById(6);

        assertNotNull(retrievedInstitution);
        assertEquals("University of Waterloo", retrievedInstitution.getName());
    }
}
