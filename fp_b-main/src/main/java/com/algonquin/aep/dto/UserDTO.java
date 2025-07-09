package com.algonquin.aep.dto;

/**
 * Data Transfer Object representing a User.
 * This class is used to transfer user data between different layers of the application,
 * containing essential user information such as credentials and user type.
 */
public class UserDTO {
    private int userId;
    private String email;
    private String password;
    private String userType;
    private String createdAt;

    /**
     * Default constructor for UserDTO.
     */
    public UserDTO() {}

    /**
     * Constructs a new UserDTO with specified parameters.
     *
     * @param id The unique identifier of the user
     * @param email The email address of the user
     * @param password The password of the user
     * @param userType The type/role of the user
     */
    public UserDTO(int id, String email, String password, String userType) {
        this.userId = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    /**
     * Gets the user ID.
     *
     * @return The unique identifier of the user
     */
    public int getUserId() { return userId; }

    /**
     * Sets the user ID.
     *
     * @param id The unique identifier to set for the user
     */
    public void setUserId(int id) { this.userId = id; }

    /**
     * Gets the user's email address.
     *
     * @return The email address of the user
     */
    public String getEmail() { return email; }

    /**
     * Sets the user's email address.
     *
     * @param email The email address to set for the user
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the user's password.
     *
     * @return The password of the user
     */
    public String getPassword() { return password; }

    /**
     * Sets the user's password.
     *
     * @param password The password to set for the user
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Gets the user type.
     *
     * @return The type/role of the user
     */
    public String getUserType() { return userType; }

    /**
     * Sets the user type.
     *
     * @param userType The type/role to set for the user
     */
    public void setUserType(String userType) { this.userType = userType; }

    /**
     * Gets the creation timestamp of the user account.
     *
     * @return The timestamp when this user account was created
     */
    public String getCreatedAt() { return createdAt; }

    /**
     * Sets the creation timestamp of the user account.
     *
     * @param createdAt The timestamp to set for when this user account was created
     */
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
