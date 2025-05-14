package config;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class JDBCUtil {

    // Add a static test connection (only used during tests)
    private static Connection testConnection = null;

    public static void setTestConnection(Connection conn) {
        testConnection = conn;
    }

    public static void clearTestConnection() {
        testConnection = null;
    }

    public static Connection getConnection() {
        // Return the test connection if it's set
        if (testConnection != null) {
            return testConnection;
        }

        Connection result = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mySQL://localhost:3308/quanlikhohang";
            String userName = "root";
            String password = "0915166497Bc#";
            result = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không th? k?t n?i ??n c? s? d? li?u !", "L?i", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public static void closeConnection(Connection c) {
        // Prevent closing test connection
        if (testConnection != null && c == testConnection) {
            return; // don't close the test connection
        }
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
