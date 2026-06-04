package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {

    public RoundButton(String text) {

        super(text);

        setFocusPainted(false);
        setContentAreaFilled(false);

        setForeground(Color.WHITE);
        setBackground(UIStyles.btn);

        setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                setBackground(
                        new Color(37, 99, 235)
                );
            }

            public void mouseExited(MouseEvent e) {
                setBackground(UIStyles.btn);
            }
        });
    }

    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());

        g2.fill(
                new RoundRectangle2D.Double(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        40,
                        40
                )
        );

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setColor(getBackground());

        g2.draw(
                new RoundRectangle2D.Double(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        40,
                        40
                )
        );
    }
}