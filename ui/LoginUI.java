package ui;

import dao.AdminDAO;
import dao.UserDAO;
import util.PopUp;
import util.RoundButton;
import util.UIStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {

        setTitle("College Event Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245,247,250));

        // TOP PANEL
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(30,41,59));
        topPanel.setPreferredSize(new Dimension(900,120));

        JLabel heading = new JLabel("COLLEGE EVENT MANAGEMENT SYSTEM");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 34));

        topPanel.add(heading);

        // CENTER PANEL
        JPanel centerPanel = new JPanel(new GridLayout(1,2,40,0));
        centerPanel.setBorder(new EmptyBorder(50,50,50,50));
        centerPanel.setBackground(new Color(245,247,250));

        // ADMIN CARD
        JPanel adminPanel = createCard(
                "ADMIN PANEL",
                "assets/admin.png"
        );

        JButton adminBtn = new RoundButton("ADMIN LOGIN");
        adminBtn.setPreferredSize(new Dimension(200,45));

        adminBtn.addActionListener(e -> adminLogin());

        adminPanel.add(Box.createVerticalStrut(15));
        adminPanel.add(adminBtn);

        // USER CARD
        JPanel userPanel = createCard(
                "USER PANEL",
                "assets/user.png"
        );

        JButton userBtn = new RoundButton("USER LOGIN");
        userBtn.setPreferredSize(new Dimension(200,45));

        userBtn.addActionListener(e -> userLogin());

        userPanel.add(Box.createVerticalStrut(15));
        userPanel.add(userBtn);

        centerPanel.add(adminPanel);
        centerPanel.add(userPanel);

        // LOGO
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(245,247,250));

        ImageIcon logo =
                new ImageIcon("assets/logo.png");

        JLabel logoLabel =
                new JLabel(logo);

        logoPanel.add(logoLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(logoPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private JPanel createCard(String title,String imagePath){

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220,220,220),1),
                new EmptyBorder(20,20,20,20)
        ));

        JLabel image =
                new JLabel(
                        new ImageIcon(imagePath)
                );

        image.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl =
                new JLabel(title);

        lbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(image);
        card.add(Box.createVerticalStrut(15));
        card.add(lbl);

        return card;
    }

    private void adminLogin() {

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        Object[] fields = {
                "Username", user,
                "Password", pass
        };

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "Admin Login",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if(option == JOptionPane.OK_OPTION){

            AdminDAO dao = new AdminDAO();

            boolean ok =
                    dao.login(
                            user.getText(),
                            new String(pass.getPassword())
                    );

            if(ok){

                PopUp.success(
                        this,
                        "Wellcome to Admin Dashboard"
                );

                new AdminUI();
                dispose();

            }else{

                PopUp.error(
                        this,
                        "Invalid Admin Login"
                );
            }
        }
    }

    private void userLogin() {

        String[] options = {
                "Login",
                "Signup"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Choose Option",
                        "User Login / SignUp",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();

        Object[] fields = {
                "Username", user,
                "Password", pass
        };

        int ok =
                JOptionPane.showConfirmDialog(
                        this,
                        fields,
                        "User Login",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if(ok != JOptionPane.OK_OPTION)
            return;

        UserDAO dao = new UserDAO();

        if(choice == 0){

            boolean success =
                    dao.login(
                            user.getText(),
                            new String(pass.getPassword())
                    );

            if(success){

                PopUp.success(
                        this,
                        "Login Successful"
                );

                new UserUI(user.getText());

                dispose();

            }else{

                PopUp.error(
                        this,
                        "Invalid Login"
                );
            }

        }else{

            boolean success =
                    dao.signup(
                            user.getText(),
                            new String(pass.getPassword())
                    );

            if(success){

                PopUp.success(
                        this,
                        "Account Created Successfully"
                );

            }else{

                PopUp.error(
                        this,
                        "User Already Exists"
                );
            }
        }
    }
}