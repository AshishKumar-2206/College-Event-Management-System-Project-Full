package dao;

import db.DBConnection;
import java.sql.*;

public class AdminDAO {

    public boolean login(String username, String password) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM admins WHERE username=? AND password=?"
                    );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}