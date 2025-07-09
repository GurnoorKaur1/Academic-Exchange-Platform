package com.algonquin.aep.dao;

import com.algonquin.aep.dto.UserDTO;

/**
 * Data Access Object interface for managing User data.
 * Provides methods to handle user-related operations in the system, including
 * user authentication, registration, and profile management.
 */
public interface UserDAO {
    /**
     * Inserts a new user into the database.
     * 
     * @param user The user data to be inserted
     */
    void insertUser(UserDTO user);

    /**
     * Retrieves a user by their email address.
     * 
     * @param email The email address to search for
     * @return The user data, or null if not found
     */
    UserDTO findUserByEmail(String email);

    /**
     * Retrieves a user by their ID.
     * 
     * @param userId The ID to search for
     * @return The user data, or null if not found
     */
    UserDTO findUserById(int userId);

    /**
     * Updates an existing user in the database.
     * 
     * @param user The updated user data
     */
    void updateUser(UserDTO user);

    /**
     * Deletes a user from the database.
     * 
     * @param userId The ID of the user to delete
     */
    void deleteUser(int userId);
}
