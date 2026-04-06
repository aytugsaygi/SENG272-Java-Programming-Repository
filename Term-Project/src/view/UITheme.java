package view;

import java.awt.*;

public class UITheme {
    // Colors
    public static final Color PRIMARY = new Color(30, 58, 138);       // Deep blue
    public static final Color PRIMARY_LIGHT = new Color(59, 130, 246); // Bright blue
    public static final Color SECONDARY = new Color(16, 185, 129);    // Emerald
    public static final Color BG_MAIN = new Color(15, 23, 42);        // Dark navy
    public static final Color BG_CARD = new Color(30, 41, 59);        // Card bg
    public static final Color BG_INPUT = new Color(51, 65, 85);       // Input bg
    public static final Color TEXT_PRIMARY = new Color(248, 250, 252);
    public static final Color TEXT_SECONDARY = new Color(148, 163, 184);
    public static final Color TEXT_MUTED = new Color(100, 116, 139);
    public static final Color ACCENT = new Color(245, 158, 11);       // Amber accent
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color BORDER = new Color(51, 65, 85);
    public static final Color STEP_ACTIVE = new Color(59, 130, 246);
    public static final Color STEP_DONE = new Color(34, 197, 94);
    public static final Color STEP_INACTIVE = new Color(71, 85, 105);
    public static final Color TABLE_HEADER = new Color(30, 58, 138);
    public static final Color TABLE_ROW_ALT = new Color(30, 41, 59);
    public static final Color TABLE_ROW = new Color(15, 23, 42);

    // Fonts
    public static Font fontTitle() {
        return new Font("Segoe UI", Font.BOLD, 22);
    }
    public static Font fontSubtitle() {
        return new Font("Segoe UI", Font.BOLD, 16);
    }
    public static Font fontBody() {
        return new Font("Segoe UI", Font.PLAIN, 13);
    }
    public static Font fontSmall() {
        return new Font("Segoe UI", Font.PLAIN, 11);
    }
    public static Font fontMono() {
        return new Font("Consolas", Font.PLAIN, 12);
    }
    public static Font fontBold() {
        return new Font("Segoe UI", Font.BOLD, 13);
    }

    // Dimensions
    public static final int PADDING = 20;
    public static final int CORNER_RADIUS = 12;

    // Style helper: configure standard button
    public static void styleButton(javax.swing.JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(fontBold());
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 24, 10, 24));
    }

    public static void styleLabel(javax.swing.JLabel lbl, Color fg, Font font) {
        lbl.setForeground(fg);
        lbl.setFont(font);
    }

    public static javax.swing.JTextField createTextField() {
        javax.swing.JTextField tf = new javax.swing.JTextField();
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setFont(fontBody());
        tf.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(BORDER, 1),
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return tf;
    }
}
