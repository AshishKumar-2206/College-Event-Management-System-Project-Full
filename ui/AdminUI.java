package ui;

import dao.EventDAO;
import util.PopUp;
import util.RoundButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class AdminUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public AdminUI() {

        setTitle("College Event Management - Admin Dashboard");
        setSize(1200,700);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(30,41,59));
        JLabel title = new JLabel("College Event Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.add(title);
        JLabel subTitle = new JLabel("Manage Events, Students and Registrations");
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        header.setLayout(new GridLayout(2,1));
        header.add(title);
        header.add(subTitle);
        add(header, BorderLayout.NORTH);

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(241,245,249));

        JPanel sidebar = new JPanel(new GridLayout(0,1,5,5));
        sidebar.setBackground(new Color(15,23,42));
        sidebar.setPreferredSize(new Dimension(250,700));
        sidebar.setBorder(new EmptyBorder(10,10,10,10));

        

String[] menus = {
    "Dashboard",
    "Add Event",
    "Update Event",
    "Delete Event",
    "View Events",
    "Search Event",
    "View Users",
    "View Participants",
    "Search Participant",
    "Logout"
};

String[] labels = {
    "📊 Dashboard",
    "➕ Add New Event",
    "✏ Edit Event",
    "🗑 Delete Event",
    "📅 Event List",
    "🔍 Search Event",
    "👥 Student List",
    "🎟 Registered Participants",
    "🔎 Search Participant",
    "🚪 Logout"
};

String[] icons = {
    "assets/dashboard.png",
    "assets/add.png",
    "assets/edit.png",
    "assets/delete.png",
    "assets/event.png",
    "assets/search.png",
    "assets/users.png",
    "assets/participants.png",
    "assets/search.png",
    "assets/logout.png"

};

for (int i = 0; i < menus.length; i++) {

    ImageIcon icon = new ImageIcon(icons[i]);

    Image img = icon.getImage().getScaledInstance(
            24,
            24,
            Image.SCALE_SMOOTH
    );

    String menu = menus[i];

    JButton btn = new JButton(labels[i], new ImageIcon(img));

    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setPreferredSize(new Dimension(200, 50));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    btn.setHorizontalAlignment(SwingConstants.LEFT);
    btn.setIconTextGap(15);
    btn.setFocusPainted(false);
    btn.setBackground(new Color(51,65,85));
    btn.setOpaque(true);
    btn.setBorderPainted(false);
    btn.setForeground(Color.WHITE);

    btn.addActionListener(e -> {
        if (menu.equals("Logout")) {
            dispose();
            new LoginUI();
        } else {
            cardLayout.show(mainPanel, menu);
        }
    });

    sidebar.add(btn);
}

        add(sidebar, BorderLayout.WEST);

        mainPanel.add(createDashboardPanel(), "Dashboard");
        mainPanel.add(createEventForm("ADD"), "Add Event");
        mainPanel.add(createEventForm("UPDATE"), "Update Event");
        mainPanel.add(createDeletePanel(), "Delete Event");
        mainPanel.add(createViewPanel("EVENTS"), "View Events");
        mainPanel.add(createSearchEventPanel(), "Search Event");
        mainPanel.add(createViewPanel("USERS"), "View Users");
        mainPanel.add(createViewPanel("PARTICIPANTS"), "View Participants");
        mainPanel.add(createSearchParticipantPanel(), "Search Participant");

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createEventForm(String mode){

        JPanel panel = new JPanel(new GridLayout(6,2,10,10));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField date = new JTextField();
        JTextField time = new JTextField();
        JTextField venue = new JTextField();

        panel.add(new JLabel("Event ID"));
        panel.add(id);
        panel.add(new JLabel("Event Name"));
        panel.add(name);
        panel.add(new JLabel("Date"));
        panel.add(date);
        panel.add(new JLabel("Time"));
        panel.add(time);
        panel.add(new JLabel("Venue"));
        panel.add(venue);

        JButton button = new RoundButton(mode);
        panel.add(new JLabel());
        panel.add(button);

        button.addActionListener(e -> {
    try {
        EventDAO dao = new EventDAO();

        int eventId = Integer.parseInt(id.getText());
        String eventName = name.getText();
        String eventDate = date.getText();
        String eventTime = time.getText();
        String eventVenue = venue.getText();

        boolean result;

        if (mode.equals("ADD")) {

            result = dao.addEvent(eventId, eventName, eventDate, eventTime, eventVenue);

            if (result)
                PopUp.success(panel, "Event Added Successfully");
            else
                PopUp.error(panel, "Failed To Add Event");

        } else {

            result = dao.updateEvent(eventId, eventName, eventDate, eventTime, eventVenue);

            if (result)
                PopUp.success(panel, "Event Updated Successfully");
            else
                PopUp.error(panel, "Failed To Update Event");
        }

    } catch (NumberFormatException ex) {
        PopUp.error(panel, "Event ID must be a number");
    } catch (Exception ex) {
        PopUp.error(panel, "Something went wrong");
    }
});

        return panel;
    }

    private JPanel createDeletePanel(){

        JPanel panel = new JPanel(new FlowLayout());

        JTextField id = new JTextField(15);
        JButton delete = new RoundButton("Delete Event");

        panel.add(new JLabel("Event ID"));
        panel.add(id);
        panel.add(delete);

        delete.addActionListener(e -> {
            try{
                int eventId = Integer.parseInt(id.getText());

                if(PopUp.confirm(panel,"Delete Event ?")){
                    EventDAO dao = new EventDAO();
                    boolean result = dao.deleteEvent(eventId);

                    if(result) PopUp.success(panel,"Deleted Successfully");
                    else PopUp.error(panel,"Event Not Found");
                }

            }catch(Exception ex){
                PopUp.error(panel,"Invalid Event ID");
            }
        });

        return panel;
    }

    private JPanel createDashboardPanel() {

    JPanel panel = new JPanel(new GridLayout(1,3,40,40));
    panel.setBorder(new EmptyBorder(40,40,40,40));
    panel.setBackground(new Color(241,245,249));

    JLabel eventLabel = new JLabel("Events: 0", SwingConstants.CENTER);
    JLabel userLabel = new JLabel("Users: 0", SwingConstants.CENTER);
    JLabel participantLabel = new JLabel("Participants: 0", SwingConstants.CENTER);
    eventLabel.setForeground(new Color(59,130,246));      // Blue
    userLabel.setForeground(new Color(16,185,129));       // Green
    participantLabel.setForeground(new Color(245,158,11)); // Orange

    JPanel eventCard = createCard("📅 TOTAL EVENTS", eventLabel);
    JPanel userCard = createCard("👥 TOTAL USERS", userLabel);
    JPanel participantCard = createCard("🎟 PARTICIPANTS", participantLabel);

    panel.add(eventCard);
    panel.add(userCard);
    panel.add(participantCard);

    EventDAO dao = new EventDAO();

    try {
        eventLabel.setText(String.valueOf(dao.totalEvents()));
        userLabel.setText(String.valueOf(dao.totalUsers()));
        participantLabel.setText(String.valueOf(dao.totalParticipants()));
    } catch(Exception e){
        e.printStackTrace();
    }

    return panel;
}

 private JPanel createCard(String title, JLabel valueLabel){

    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);

    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180,180,180),2),
            new EmptyBorder(25,25,25,25)
    ));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));

    card.add(titleLabel, BorderLayout.NORTH);
    card.add(valueLabel, BorderLayout.CENTER);

    return card;
}

private JPanel createSearchEventPanel() {

    JPanel panel = new JPanel(new BorderLayout());

    JPanel top = new JPanel();
    JTextField idField = new JTextField(10);
    JButton search = new RoundButton("Search");

    top.add(new JLabel("Event ID: "));
    top.add(idField);
    top.add(search);

    JTextArea area = new JTextArea();
    area.setFont(new Font("Consolas", Font.PLAIN, 14));

    panel.add(top, BorderLayout.NORTH);
    panel.add(new JScrollPane(area), BorderLayout.CENTER);

    search.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            EventDAO dao = new EventDAO();
            ResultSet rs = dao.getEventById(id);

            area.setText("");

            if (rs.next()) {
                area.append(
                        rs.getInt(1) + " | " +
                        rs.getString(2) + " | " +
                        rs.getString(3) + " | " +
                        rs.getString(4) + " | " +
                        rs.getString(5)
                );
            } else {
                area.setText("No Event Found");
            }

        } catch (Exception ex) {
            area.setText("Invalid Input");
        }
    });

    return panel;
}
private JPanel createSearchParticipantPanel() {

    JPanel panel = new JPanel(new BorderLayout());

    JPanel top = new JPanel();
    JTextField idField = new JTextField(10);
    JButton search = new RoundButton("Search");

    top.add(new JLabel("Participant Name: "));
    top.add(idField);
    top.add(search);

    JTextArea area = new JTextArea();

    panel.add(top, BorderLayout.NORTH);
    panel.add(new JScrollPane(area), BorderLayout.CENTER);

    search.addActionListener(e -> {

        try {

            String name = idField.getText().trim();

            EventDAO dao = new EventDAO();
            ResultSet rs = dao.searchParticipant(name);

            area.setText("");

            boolean found = false;

            while(rs.next()) {

                found = true;

                area.append(
                    "========================================\n" +
                    " Student Name : " + rs.getString(1) + "\n" +
                    " Event Name   : " + rs.getString(2) + "\n" +
                    " Date         : " + rs.getString(3) + "\n" +
                    " Time         : " + rs.getString(4) + "\n" +
                    " Venue        : " + rs.getString(5) + "\n" +
                    "========================================\n\n"
                );
            }

            if(!found) {
                area.setText("No Participant Found");
            }

        } catch(Exception ex) {

            area.setText("Search Failed");

        }

    });

    return panel;
}
    private JPanel createViewPanel(String type) {

    JPanel panel = new JPanel(new BorderLayout(15,15));
    panel.setBackground(new Color(241,245,249));
    panel.setBorder(new EmptyBorder(20,20,20,20));

    JButton load = new JButton("Load Data");
    load.setFont(new Font("Segoe UI", Font.BOLD, 14));
    load.setBackground(new Color(37,99,235));
    load.setForeground(Color.WHITE);

    JTable table = new JTable();
    table.setRowHeight(30);
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0,0));
    table.setSelectionBackground(new Color(37,99,235));
    table.setSelectionForeground(Color.WHITE);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 14)
    );

    JScrollPane scroll = new JScrollPane(table);

    panel.add(load, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);

    load.addActionListener(e -> {

        try {

            EventDAO dao = new EventDAO();
            ResultSet rs = null;

            DefaultTableModel model;

            if(type.equals("EVENTS")) {

                model = new DefaultTableModel(
                        new String[]{
                                "ID",
                                "Event Name",
                                "Date",
                                "Time",
                                "Venue"
                        },0);

                rs = dao.getAllEvents();

                while(rs.next()) {

                    model.addRow(new Object[]{
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    });
                }

            }
            else if(type.equals("USERS")) {

                model = new DefaultTableModel(
                        new String[]{"Username"},0);

                rs = dao.getAllUsers();

                while(rs.next()) {

                    model.addRow(new Object[]{
                            rs.getString(1)
                    });
                }

            }
            else {

                model = new DefaultTableModel(
                        new String[]{
                                "Student",
                                "Event",
                                "Date",
                                "Time",
                                "Venue"
                        },0);

                rs = dao.getParticipants();

                while(rs.next()) {

                    model.addRow(new Object[]{
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    });
                }
            }

            table.setModel(model);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    });

    return panel;
   }
}
