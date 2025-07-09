/**
 * Implementation of the UserDAO interface.
 * This class handles all database operations related to user management,
 * including user authentication, profile management, and user data retrieval.
 * It provides secure methods for storing and accessing user information.
 */
package com.algonquin.aep.dao;

import com.algonquin.aep.dto.UserDTO;
import com.algonquin.aep.util.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * UserDAOImpl class provides methods for user data access and manipulation.
 * It implements the UserDAO interface and provides a connection to the database.
 */
public class UserDAOImpl implements UserDAO {
    private Connection connection;

    /**
     * Constructs a new UserDAOImpl instance.
     * Initializes the database connection using DBConnection singleton.
     */
    public UserDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new user into the database.
     * 
     * @param user The UserDTO containing user information
     */
    @Override
    public void insertUser(UserDTO user) {
        // int userId = -1;
        try {
            String sql = "INSERT INTO users (email, password, user_type) VALUES (?, ?, ?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    user.setUserId(userId);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // return userId;
    }

    /**
     * Retrieves a user by their email.
     * 
     * @param email The email of the user to retrieve
     * @return UserDTO object if found, null otherwise
     */
    @Override
    public UserDTO findUserByEmail(String email) {
        UserDTO user = null;
        try {
            String sql = "SELECT user_id, email, password, user_type FROM users WHERE email = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                // You may want to fetch additional details from the specific table (professional or institution)
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param userId The ID of the user to retrieve
     * @return UserDTO object if found, null otherwise
     */
    @Override
    public UserDTO findUserById(int userId) {
        UserDTO user = null;
        try {
            String sql = "SELECT user_id, email, password, user_type, created_at FROM users WHERE user_id = ?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                
                // Format the creation date to only include the date part
                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(createdAt);
                    user.setCreatedAt(formattedDate);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    /**
     * Updates an existing user's information.
     * 
     * @param user The UserDTO containing updated user information
     */
    @Override
    public void updateUser(UserDTO user) {
        // Implement update logic

    }

    /**
     * Deletes a user by their ID.
     * 
     * @param userId The ID of the user to delete
     */
    @Override
    public void deleteUser(int userId) {
        // Implement delete logic
    }
}
