package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/pos_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password"; // Set your database password

    private static HikariDataSource dataSource;

    // Initialize connection pool (true singleton pattern)
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database connection pool", e);
        }
    }

    // Get a connection from the pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Helper method to close resources
    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("Error closing resource: " + e.getMessage());
                }
            }
        }
    }

    // For application shutdown
    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}