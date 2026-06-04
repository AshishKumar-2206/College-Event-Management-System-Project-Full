package dao;

import db.DBConnection;
import java.sql.*;

public class EventDAO {

    public boolean addEvent(
            int id,
            String name,
            String date,
            String time,
            String venue
    ) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "INSERT INTO events VALUES(?,?,?,?,?)"
                    );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.setString(5, venue);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet getEventById(int id) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT * FROM events WHERE id=?"
                    );

            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getParticipantById(int id) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "SELECT r.username,e.name,e.date,e.time,e.venue " +
                            "FROM registrations r " +
                            "JOIN events e ON r.event_id=e.id " +
                            "WHERE r.id=?"
                    );

            ps.setInt(1, id);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateEvent(
            int id,
            String name,
            String date,
            String time,
            String venue
    ) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "UPDATE events SET name=?,date=?,time=?,venue=? WHERE id=?"
                    );

            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, time);
            ps.setString(4, venue);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteEvent(int id) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                            "DELETE FROM events WHERE id=?"
                    );

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ResultSet getAllEvents() {

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            return st.executeQuery(
                    "SELECT * FROM events"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet getAllUsers() {

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            return st.executeQuery(
                    "SELECT * FROM users"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet searchEvent(String keyword){

    try{
        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "SELECT * FROM events WHERE name LIKE ?"
                );

        ps.setString(1,"%"+keyword+"%");

        return ps.executeQuery();

    }catch(Exception e){
        e.printStackTrace();
    }

    return null;
   }
   public ResultSet searchUser(String keyword){

    try{
        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "SELECT * FROM users WHERE username LIKE ?"
                );

        ps.setString(1,"%"+keyword+"%");

        return ps.executeQuery();

    }catch(Exception e){
        e.printStackTrace();
    }

    return null;
    }
    public ResultSet getParticipants() {

    try {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        return st.executeQuery(
                "SELECT r.username,e.name,e.date,e.time,e.venue " +
                "FROM registrations r " +
                "JOIN events e ON r.event_id=e.id"
        );

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
    }
    public ResultSet searchParticipant(String keyword){

    try{

        Connection con = DBConnection.getConnection();

        PreparedStatement ps =
                con.prepareStatement(
                        "SELECT r.username,e.name,e.date,e.time,e.venue " +
                        "FROM registrations r " +
                        "JOIN events e ON r.event_id=e.id " +
                        "WHERE r.username LIKE ?"
                );

        ps.setString(1,"%"+keyword+"%");

        return ps.executeQuery();

    }catch(Exception e){
        e.printStackTrace();
    }

    return null;
    }
    public int totalEvents(){

    try{

        Connection con = DBConnection.getConnection();

        ResultSet rs =
                con.createStatement()
                        .executeQuery(
                                "SELECT COUNT(*) FROM events"
                        );

        if(rs.next())
            return rs.getInt(1);

    }catch(Exception e){
        e.printStackTrace();
    }

    return 0;
    }
    public int totalUsers(){

    try{

        Connection con = DBConnection.getConnection();

        ResultSet rs =
                con.createStatement()
                        .executeQuery(
                                "SELECT COUNT(*) FROM users"
                        );

        if(rs.next())
            return rs.getInt(1);

    }catch(Exception e){
        e.printStackTrace();
    }

    return 0;
    }
    public int totalParticipants(){

    try{

        Connection con = DBConnection.getConnection();

        ResultSet rs =
                con.createStatement()
                        .executeQuery(
                                "SELECT COUNT(*) FROM registrations"
                        );

        if(rs.next())
            return rs.getInt(1);

    }catch(Exception e){
        e.printStackTrace();
    }

    return 0;
    }
}

