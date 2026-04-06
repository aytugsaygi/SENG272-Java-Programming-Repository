package view;

import controller.AppController;
import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private AppController controller;
    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionField;

    public ProfilePanel(AppController controller) {
        this.controller = controller;
        setBackground(UITheme.BG_MAIN);
        setLayout(new GridBagLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(UITheme.BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(460, 400));
        card.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 0, 6, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.gridx = 0;

        // Icon + Title
        JLabel icon = new JLabel("👤");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        gc.gridy = 0;
        gc.insets = new Insets(0, 0, 4, 0);
        card.add(icon, gc);

        JLabel title = new JLabel("Session Profile");
        title.setFont(UITheme.fontTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gc.gridy = 1;
        gc.insets = new Insets(0, 0, 4, 0);
        card.add(title, gc);

        JLabel subtitle = new JLabel("Enter your information to begin the measurement process");
        subtitle.setFont(UITheme.fontSmall());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gc.gridy = 2;
        gc.insets = new Insets(0, 0, 24, 0);
        card.add(subtitle, gc);

        // Username
        gc.gridy = 3;
        gc.insets = new Insets(0, 0, 4, 0);
        card.add(makeLabel("Username"), gc);
        usernameField = UITheme.createTextField();
        usernameField.setToolTipText("Enter your username");
        gc.gridy = 4;
        gc.insets = new Insets(0, 0, 14, 0);
        card.add(usernameField, gc);

        // School
        gc.gridy = 5;
        gc.insets = new Insets(0, 0, 4, 0);
        card.add(makeLabel("School"), gc);
        schoolField = UITheme.createTextField();
        schoolField.setToolTipText("Enter your school or organization");
        gc.gridy = 6;
        gc.insets = new Insets(0, 0, 14, 0);
        card.add(schoolField, gc);

        // Session Name
        gc.gridy = 7;
        gc.insets = new Insets(0, 0, 4, 0);
        card.add(makeLabel("Session Name"), gc);
        sessionField = UITheme.createTextField();
        sessionField.setToolTipText("Enter a name for this session");
        gc.gridy = 8;
        gc.insets = new Insets(0, 0, 28, 0);
        card.add(sessionField, gc);

        // Next button
        JButton nextBtn = new JButton("Next: Define Quality →");
        UITheme.styleButton(nextBtn, UITheme.PRIMARY_LIGHT, Color.WHITE);
        nextBtn.setPreferredSize(new Dimension(0, 42));
        gc.gridy = 9;
        gc.insets = new Insets(0, 0, 0, 0);
        card.add(nextBtn, gc);

        nextBtn.addActionListener(e -> onNext());

        add(card);
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UITheme.fontBold());
        lbl.setForeground(UITheme.TEXT_SECONDARY);
        return lbl;
    }

    private void onNext() {
        String username = usernameField.getText().trim();
        String school = schoolField.getText().trim();
        String session = sessionField.getText().trim();

        if (username.isEmpty()) {
            showWarning("Please enter your username to continue.");
            usernameField.requestFocus();
            return;
        }
        if (school.isEmpty()) {
            showWarning("Please enter your school to continue.");
            schoolField.requestFocus();
            return;
        }
        if (session.isEmpty()) {
            showWarning("Please enter a session name to continue.");
            sessionField.requestFocus();
            return;
        }

        controller.getSession().setUsername(username);
        controller.getSession().setSchool(school);
        controller.getSession().setSessionName(session);

        controller.markStepCompleted(0);
        controller.nextStep();
    }

    private void showWarning(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Missing Information");
        dialog.setVisible(true);
    }
}
