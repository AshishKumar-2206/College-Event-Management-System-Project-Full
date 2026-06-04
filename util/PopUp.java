package util;

import javax.swing.*;
import java.awt.*;

public class PopUp {

    public static void success(Component c, String msg) {
        JOptionPane.showMessageDialog(
                c,
                msg,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void error(Component c, String msg) {
        JOptionPane.showMessageDialog(
                c,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public static boolean confirm(Component c, String msg) {
        return JOptionPane.showConfirmDialog(
                c,
                msg,
                "Confirm",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }
}