package com.algonquin.aep;

import com.algonquin.aep.dao.UserDAO;
import com.algonquin.aep.dao.UserDAOImpl;
import com.algonquin.aep.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserDAO class.
 * This class tests the CRUD operations for User entities.
 */
class UserDAOTest {

    private UserDAO userDAO;

    /**
     * Sets up the test environment before each test.
     * Initializes the UserDAO implementation.
     */
    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }

    /**
     * Tests the insertion of a new User entity.
     * Verifies that the entity can be retrieved by its email and matches the expected values.
     */
    @Test
    void testInsertUser() {
        UserDTO user = new UserDTO();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUserType("professional");

        userDAO.insertUser(user);
        UserDTO retrievedUser = userDAO.findUserByEmail("test@example.com");

        assertNotNull(retrievedUser);
        assertEquals("test@example.com", retrievedUser.getEmail());
        assertEquals("professional", retrievedUser.getUserType());
    }

    /**
     * Tests the retrieval of a User entity by its email.
     * Verifies that the retrieved entity matches the expected values.
     */
    @Test
    void testFindUserByEmail() {
        UserDTO user = new UserDTO();
        user.setEmail("findme@example.com");
        user.setPassword("findpassword");
        user.setUserType("institution");

        userDAO.insertUser(user);
        UserDTO retrievedUser = userDAO.findUserByEmail("findme@example.com");

        assertNotNull(retrievedUser);
        assertEquals("findme@example.com", retrievedUser.getEmail());
    }
}
