package com.algonquin.aep.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class implementing the Singleton pattern.
 * This class manages a single database connection instance for the Academic Expert Platform,
 * ensuring that only one connection is created and reused throughout the application lifecycle.
 *
 * The class uses MySQL JDBC driver to establish a connection to the database.
 * Connection parameters (URL, username, password) are defined as constants.
 *
 * Usage example:
 * <pre>
 * try {
 *     DBConnection dbConnection = DBConnection.getInstance();
 *     Connection conn = dbConnection.getConnection();
 *     // Use the connection for database operations
 * } catch (SQLException e) {
 *     // Handle connection error
 * }
 * </pre>
 */
public class DBConnection {
    /** The single instance of DBConnection */
    private static DBConnection dbConnectionSingleton;
    
    /** The database connection object */
    private Connection connection;
    
    /** The URL of the MySQL database server */
    private static final String ServerURL = "jdbc:mysql://localhost:3306/aep";
    
    /** Database username */
    private static final String USER = "root";
    
    /** Database password */
    private static final String PASSWORD = "11111111";
    
    /** The fully qualified name of the JDBC driver class */
    private static final String driverString = "com.mysql.cj.jdbc.Driver";

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection using the configured parameters.
     * Loads the JDBC driver and establishes the connection.
     *
     * If connection fails, an error message is printed to System.out.
     */
    private DBConnection() {
        try {
            Class.forName(driverString);
            this.connection = DriverManager.getConnection(ServerURL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Connection was not successful: " + ex.getMessage());
        }
    }

    /**
     * Returns the singleton instance of DBConnection.
     * If the instance doesn't exist, creates a new one.
     *
     * @return The singleton instance of DBConnection
     * @throws SQLException If a database access error occurs or the connection cannot be established
     */
    public static DBConnection getInstance() throws SQLException {
        if (dbConnectionSingleton == null) {
            dbConnectionSingleton = new DBConnection();
        }
        return dbConnectionSingleton;
    }

    /**
     * Returns the database connection object.
     * This connection can be used for executing SQL statements.
     *
     * @return The Connection object representing the database connection
     */
    public Connection getConnection() {
        return connection;
    }
}