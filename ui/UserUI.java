package ui;

import dao.EventDAO;
import dao.UserDAO;
import util.PopUp;
import util.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class UserUI extends JFrame {

    private JComboBox<String> eventCombo;
    private String username;

    public UserUI(String username) {

        this.username = username;

        setTitle("User Dashboard");
        setSize(600,300);
        setLayout(new FlowLayout());

        JLabel label =
                new JLabel("Available Events");

        eventCombo =
                new JComboBox<>();

        JButton registerBtn =
                new RoundButton("Register");

        JButton refreshBtn =
                new RoundButton("Refresh");

        add(label);
        add(eventCombo);
        add(registerBtn);
        add(refreshBtn);

        loadEvents();

        refreshBtn.addActionListener(e -> {
            loadEvents();
        });

        registerBtn.addActionListener(e -> {
            registerEvent();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadEvents() {

        try {

            eventCombo.removeAllItems();

            EventDAO dao =
                    new EventDAO();

            ResultSet rs =
                    dao.getAllEvents();

            while(rs.next()) {

                eventCombo.addItem(
                        rs.getInt("id")
                                + " - "
                                + rs.getString("name")
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    private void registerEvent() {

        try {

            String selected =
                    (String) eventCombo.getSelectedItem();

            if(selected == null) {

                PopUp.error(
                        this,
                        "No Event Available"
                );

                return;
            }

            int eventId =
                    Integer.parseInt(
                            selected.split("-")[0].trim()
                    );

            UserDAO dao =
                    new UserDAO();

            boolean success =
                    dao.registerEvent(
                            username,
                            eventId
                    );

            if(!success) {

                PopUp.error(
                        this,
                        "Already Registered"
                );

                return;
            }

            EventDAO eventDAO =
                    new EventDAO();

            ResultSet rs =
                    eventDAO.getAllEvents();

            while(rs.next()) {

                if(rs.getInt("id") == eventId) {

                    PopUp.success(
                            this,
                            "Registration Successful\n\n"
                                    + "Event : "
                                    + rs.getString("name")
                                    + "\nDate : "
                                    + rs.getString("date")
                                    + "\nTime : "
                                    + rs.getString("time")
                                    + "\nVenue : "
                                    + rs.getString("venue")
                    );

                    break;
                }
            }

        } catch(Exception e) {

            e.printStackTrace();

            PopUp.error(
                    this,
                    "Registration Failed"
            );
        }
    }
}