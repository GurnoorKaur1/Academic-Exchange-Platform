package com.algonquin.aep.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserDTO
 * Tests the basic functionality of the UserDTO class including getters, setters,
 * and data validation
 */
public class UserDTOTest {
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        // Initialize a new UserDTO instance before each test
        userDTO = new UserDTO();
    }

    @Test
    @DisplayName("Test email getter and setter")
    public void testSetAndGetEmail() {
        String expectedEmail = "test@example.com";
        userDTO.setEmail(expectedEmail);
        assertEquals(expectedEmail, userDTO.getEmail(), "Email should match the set value");
    }

    @Test
    @DisplayName("Test password getter and setter")
    public void testSetAndGetPassword() {
        String expectedPassword = "password123";
        userDTO.setPassword(expectedPassword);
        assertEquals(expectedPassword, userDTO.getPassword(), "Password should match the set value");
    }

    @Test
    @DisplayName("Test user type getter and setter")
    public void testSetAndGetUserType() {
        String expectedUserType = "professional";
        userDTO.setUserType(expectedUserType);
        assertEquals(expectedUserType, userDTO.getUserType(), "User type should match the set value");
    }

    @Test
    @DisplayName("Test user ID getter and setter")
    public void testSetAndGetUserId() {
        int expectedId = 1;
        userDTO.setUserId(expectedId);
        assertEquals(expectedId, userDTO.getUserId(), "User ID should match the set value");
    }

    @Test
    @DisplayName("Test constructor with parameters")
    public void testParameterizedConstructor() {
        int id = 1;
        String email = "test@example.com";
        String password = "password123";
        String userType = "professional";

        UserDTO newUserDTO = new UserDTO(id, email, password, userType);

        assertEquals(id, newUserDTO.getUserId(), "User ID should match constructor parameter");
        assertEquals(email, newUserDTO.getEmail(), "Email should match constructor parameter");
        assertEquals(password, newUserDTO.getPassword(), "Password should match constructor parameter");
        assertEquals(userType, newUserDTO.getUserType(), "User type should match constructor parameter");
    }
}