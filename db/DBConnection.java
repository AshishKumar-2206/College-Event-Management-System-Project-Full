package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/event_system_new";

    private static final String USER = "root";

    private static final String PASSWORD = "Ashish@2004";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            initializeDatabase(con);

            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void initializeDatabase(Connection con) {

        try {

            Statement st = con.createStatement();

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS admins(" +
                    "username VARCHAR(50)," +
                    "password VARCHAR(50))"
            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users(" +
                    "username VARCHAR(50) UNIQUE," +
                    "password VARCHAR(50))"
            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS events(" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "date VARCHAR(50)," +
                    "time VARCHAR(50)," +
                    "venue VARCHAR(100))"
            );

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS registrations(" +
                    "username VARCHAR(50)," +
                    "event_id INT," +
                    "UNIQUE(username,event_id))"
            );

            st.executeUpdate(
                    "INSERT IGNORE INTO admins VALUES('admin','admin123')"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}