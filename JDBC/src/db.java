import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.cj.xdevapi.Result;

public class db {

    final String DB_URL = "jdbc:mysql://localhost:3306/";
    final String USER = "root";
    final String PASS = "#$Kamla@21";

    private Connection conn;
    private Statement stmt;

    public db() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String use = "USE students";
            stmt.execute(use);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            String createTableSQL1 = "CREATE TABLE IF NOT EXISTS users ("
                    + "username VARCHAR(255) PRIMARY KEY,"
                    + "pass VARCHAR(255) NOT NULL)";

            String createTableSQL2 = "CREATE TABLE IF NOT EXISTS user_thoughts ("
                    + "username VARCHAR(255),"
                    + "thoughts VARCHAR(255))";

            stmt.executeUpdate(createTableSQL1);
            stmt.executeUpdate(createTableSQL2);

            // System.out.println("Tables created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSQL1(String username, String pass) {
        try {
            // Try to insert the new user
            String insertSql = "INSERT INTO users (username, pass) VALUES (?, ?)";
            try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                insertPstmt.setString(1, username);
                insertPstmt.setString(2, pass);
                insertPstmt.executeUpdate();
                System.out.println("User added successfully...");
            }
        } catch (SQLException e) {
            // User already exists or another SQL error occurred
            // Handle the specific exception based on your requirements
            // Check if the user already exists

            try {
                String selectSql = "SELECT * FROM users WHERE username = ? and pass=?";
                try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                    selectPstmt.setString(1, username);
                    selectPstmt.setString(2, pass);
                    try (ResultSet resultSet = selectPstmt.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Welcome Existing user " + username + "! Login successful...");
                        } else {
                            System.out.println("Please Enter correct password to Login!");
                            System.exit(0);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

    public void executeSQl2(String username, String thoughts) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user_thoughts VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, thoughts);
            pstmt.executeUpdate();
            System.out.println("Thought added successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showThoughts(String username) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT thoughts FROM user_thoughts WHERE username=?");
            pstmt.setString(1, username);

            ResultSet resultSet = pstmt.executeQuery();

            // Process the result set, for example:
            while (resultSet.next()) {
                int num = 1;
                String thought = resultSet.getString("thoughts");
                System.out.println("Thought: " + num + thought);
                num++;
            }

            // Close the ResultSet and PreparedStatement
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showParticularThought(String username, String partialThought) {
        try {
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT thoughts FROM user_thoughts WHERE username=? and thoughts LIKE ?");
            pstmt.setString(1, username);
            pstmt.setString(2, "%" + partialThought + "%");

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Process the result set, for example:
                do {
                    String thought = resultSet.getString("thoughts");
                    System.out.println("Thought : " + thought);
                } while (resultSet.next());

            } else {
                System.out.println("No Result is Found!");
            }
            // Close the ResultSet and PreparedStatement
            resultSet.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteThought(String username, String partialThought) {
        try {
            // Use a partial match in the WHERE clause
            String deleteSql = "DELETE FROM user_thoughts WHERE username = ? AND thoughts LIKE ?";
            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                deletePstmt.setString(1, username);
                deletePstmt.setString(2, "%" + partialThought + "%");
                int rowsAffected = deletePstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Thought deleted successfully...");
                } else {
                    System.out.println("No matching thoughts found for deletion.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllThoughts(String username) {
        try {
            String deleteSql = "DELETE FROM user_thoughts WHERE username=?";
            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                deletePstmt.setString(1, username);
                int rowsDeleted = deletePstmt.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Thoughts for user " + username + " deleted successfully...");
                } else {
                    System.out.println("No thoughts found for user " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String username, String newPassword) {
        try {
            String updateSql = "UPDATE users SET pass = ? WHERE username = ?";
            try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                updatePstmt.setString(1, newPassword);
                updatePstmt.setString(2, username);

                int rowsAffected = updatePstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password updated successfully for user: " + username);
                } else {
                    System.out.println("User not found. Password update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Connection closed successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
