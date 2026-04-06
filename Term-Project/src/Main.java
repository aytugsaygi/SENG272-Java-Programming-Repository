import controller.AppController;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Panel.background", new java.awt.Color(15, 23, 42));
        UIManager.put("OptionPane.background", new java.awt.Color(30, 41, 59));
        UIManager.put("OptionPane.messageForeground", new java.awt.Color(248, 250, 252));
        UIManager.put("Button.background", new java.awt.Color(51, 65, 85));
        UIManager.put("Button.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("TextField.background", new java.awt.Color(51, 65, 85));
        UIManager.put("TextField.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("TextField.caretForeground", new java.awt.Color(248, 250, 252));
        UIManager.put("ComboBox.background", new java.awt.Color(51, 65, 85));
        UIManager.put("ComboBox.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("ScrollBar.thumb", new java.awt.Color(71, 85, 105));
        UIManager.put("ScrollBar.track", new java.awt.Color(30, 41, 59));
        UIManager.put("Table.background", new java.awt.Color(15, 23, 42));
        UIManager.put("Table.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("TableHeader.background", new java.awt.Color(30, 58, 138));
        UIManager.put("TableHeader.foreground", java.awt.Color.WHITE);
        UIManager.put("RadioButton.background", new java.awt.Color(30, 41, 59));
        UIManager.put("RadioButton.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("Label.foreground", new java.awt.Color(248, 250, 252));
        UIManager.put("ScrollPane.background", new java.awt.Color(15, 23, 42));

        SwingUtilities.invokeLater(() -> new AppController());
    }
}
