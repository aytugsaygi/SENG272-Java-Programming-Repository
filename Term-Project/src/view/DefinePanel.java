package view;

import controller.AppController;
import model.Scenario;
import model.ScenarioRepository;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DefinePanel extends JPanel {
    private AppController controller;

    private ButtonGroup qualityTypeGroup;
    private JRadioButton productQualityBtn;
    private JRadioButton processQualityBtn;

    private ButtonGroup modeGroup;
    private JRadioButton customModeBtn;
    private JRadioButton healthModeBtn;
    private JRadioButton educationModeBtn;

    private ButtonGroup scenarioGroup;
    private JPanel scenarioPanel;

    public DefinePanel(AppController controller) {
        this.controller = controller;
        setBackground(UITheme.BG_MAIN);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel scrollContent = new JPanel();
        scrollContent.setBackground(UITheme.BG_MAIN);
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel title = new JLabel("Step 2: Define Quality Dimensions");
        title.setFont(UITheme.fontTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(title);
        scrollContent.add(Box.createVerticalStrut(6));

        JLabel subtitle = new JLabel("Select quality type, measurement mode, and scenario");
        subtitle.setFont(UITheme.fontBody());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(subtitle);
        scrollContent.add(Box.createVerticalStrut(24));

        scrollContent.add(makeSectionLabel("2a. Quality Type Selection"));
        scrollContent.add(Box.createVerticalStrut(10));

        JPanel qualityCard = makeCard();
        qualityCard.setLayout(new GridLayout(1, 2, 12, 0));

        qualityTypeGroup = new ButtonGroup();
        productQualityBtn = makeRadioCard("Product Quality",
                "Software product characteristics:\nperformance, security, usability, reliability");
        processQualityBtn = makeRadioCard("Process Quality",
                "Development process characteristics:\nsprint efficiency, code quality, team collaboration");
        qualityTypeGroup.add(productQualityBtn);
        qualityTypeGroup.add(processQualityBtn);
        productQualityBtn.setSelected(true);
        qualityCard.add(wrapRadioCard(productQualityBtn, "Product Quality",
                "Software product characteristics:\nperformance, security, usability, reliability"));
        qualityCard.add(wrapRadioCard(processQualityBtn, "Process Quality",
                "Development process characteristics:\nsprint efficiency, code quality, team collaboration"));

        scrollContent.add(qualityCard);
        scrollContent.add(Box.createVerticalStrut(20));

        scrollContent.add(makeSectionLabel("2b. Mode Selection"));
        scrollContent.add(Box.createVerticalStrut(10));

        JPanel modeCard = makeCard();
        modeCard.setLayout(new GridLayout(1, 3, 12, 0));
        modeGroup = new ButtonGroup();
        customModeBtn = new JRadioButton("Custom");
        healthModeBtn = new JRadioButton("Health");
        educationModeBtn = new JRadioButton("Education");
        modeGroup.add(customModeBtn);
        modeGroup.add(healthModeBtn);
        modeGroup.add(educationModeBtn);
        healthModeBtn.setSelected(true);

        modeCard.add(
                wrapModeCard(customModeBtn, "Custom", "Define your own dimensions and metrics from scratch (Bonus)"));
        modeCard.add(wrapModeCard(healthModeBtn, "Health", "Health management system scenarios (ready-made dataset)"));
        modeCard.add(
                wrapModeCard(educationModeBtn, "Education", "Education LMS system scenarios (ready-made dataset)"));

        ActionListener modeListener = (ActionEvent e) -> updateScenarios();
        customModeBtn.addActionListener(modeListener);
        healthModeBtn.addActionListener(modeListener);
        educationModeBtn.addActionListener(modeListener);

        scrollContent.add(modeCard);
        scrollContent.add(Box.createVerticalStrut(20));

        scrollContent.add(makeSectionLabel("2c. Scenario Selection"));
        scrollContent.add(Box.createVerticalStrut(10));

        scenarioPanel = new JPanel();
        scenarioPanel.setBackground(UITheme.BG_MAIN);
        scenarioPanel.setLayout(new BoxLayout(scenarioPanel, BoxLayout.Y_AXIS));
        scenarioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        scenarioGroup = new ButtonGroup();
        scrollContent.add(scenarioPanel);
        scrollContent.add(Box.createVerticalStrut(24));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setOpaque(false);
        navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("← Back");
        UITheme.styleButton(backBtn, UITheme.BG_INPUT, UITheme.TEXT_SECONDARY);
        backBtn.addActionListener(e -> controller.previousStep());

        JButton nextBtn = new JButton("Next: Plan →");
        UITheme.styleButton(nextBtn, UITheme.PRIMARY_LIGHT, Color.WHITE);
        nextBtn.addActionListener(e -> onNext());

        navPanel.add(backBtn);
        navPanel.add(nextBtn);
        scrollContent.add(navPanel);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBackground(UITheme.BG_MAIN);
        scrollPane.getViewport().setBackground(UITheme.BG_MAIN);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        updateScenarios();
    }

    private void updateScenarios() {
        scenarioPanel.removeAll();
        scenarioGroup = new ButtonGroup();
        String mode = getSelectedMode();
        List<Scenario> scenarios = ScenarioRepository.getScenariosForMode(mode);

        for (Scenario s : scenarios) {
            JRadioButton btn = new JRadioButton(s.getName());
            btn.setBackground(UITheme.BG_CARD);
            btn.setForeground(UITheme.TEXT_PRIMARY);
            btn.setFont(UITheme.fontBody());
            btn.setFocusPainted(false);
            btn.putClientProperty("scenario", s);

            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UITheme.BG_CARD);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.setColor(UITheme.BORDER);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                    g2.dispose();
                }
            };
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            row.add(btn);
            scenarioPanel.add(row);
            scenarioPanel.add(Box.createVerticalStrut(6));
            scenarioGroup.add(btn);
        }

        if (!scenarios.isEmpty()) {
            Component first = scenarioPanel.getComponent(0);
            if (first instanceof JPanel) {
                for (Component c : ((JPanel) first).getComponents()) {
                    if (c instanceof JRadioButton) {
                        ((JRadioButton) c).setSelected(true);
                        break;
                    }
                }
            }
        }

        scenarioPanel.revalidate();
        scenarioPanel.repaint();
    }

    private String getSelectedMode() {
        if (customModeBtn.isSelected())
            return "Custom";
        if (healthModeBtn.isSelected())
            return "Health";
        if (educationModeBtn.isSelected())
            return "Education";
        return "Health";
    }

    private Scenario getSelectedScenario() {
        for (Component c : scenarioPanel.getComponents()) {
            if (c instanceof JPanel) {
                for (Component cc : ((JPanel) c).getComponents()) {
                    if (cc instanceof JRadioButton) {
                        JRadioButton rb = (JRadioButton) cc;
                        if (rb.isSelected()) {
                            return (Scenario) rb.getClientProperty("scenario");
                        }
                    }
                }
            }
        }
        return null;
    }

    private void onNext() {
        Scenario selected = getSelectedScenario();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a scenario to continue.", "Selection Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.getSession().setQualityType(productQualityBtn.isSelected() ? "Product Quality" : "Process Quality");
        controller.getSession().setMode(getSelectedMode());
        controller.getSession().setSelectedScenario(selected);
        controller.markStepCompleted(1);
        controller.nextStep();
    }

    private JLabel makeSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UITheme.fontSubtitle());
        lbl.setForeground(UITheme.PRIMARY_LIGHT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JPanel makeCard() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

            }
        };
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        return panel;
    }

    private JRadioButton makeRadioCard(String name, String desc) {
        JRadioButton btn = new JRadioButton(name);
        btn.setOpaque(false);
        btn.setForeground(UITheme.TEXT_PRIMARY);
        btn.setFont(UITheme.fontBold());
        btn.setFocusPainted(false);
        return btn;
    }

    private JPanel wrapRadioCard(JRadioButton btn, String name, String desc) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.isSelected() ? new Color(30, 58, 138, 80) : UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(btn.isSelected() ? UITheme.PRIMARY_LIGHT : UITheme.BORDER);
                g2.setStroke(new BasicStroke(btn.isSelected() ? 2 : 1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(UITheme.fontBold());
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);

        String[] lines = desc.split("\n");
        JLabel descLabel = new JLabel("<html>" + lines[0] + (lines.length > 1 ? "<br>" + lines[1] : "") + "</html>");
        descLabel.setFont(UITheme.fontSmall());
        descLabel.setForeground(UITheme.TEXT_SECONDARY);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        top.setOpaque(false);
        top.add(btn);

        card.add(top, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        btn.addActionListener(e -> card.repaint());
        btn.addChangeListener(e -> card.repaint());

        return card;
    }

    private JPanel wrapModeCard(JRadioButton btn, String name, String desc) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.isSelected() ? new Color(16, 185, 129, 40) : UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(btn.isSelected() ? UITheme.SECONDARY : UITheme.BORDER);
                g2.setStroke(new BasicStroke(btn.isSelected() ? 2 : 1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(UITheme.fontBold());
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);

        JLabel descLabel = new JLabel("<html>" + desc + "</html>");
        descLabel.setFont(UITheme.fontSmall());
        descLabel.setForeground(UITheme.TEXT_SECONDARY);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        top.setOpaque(false);
        top.add(btn);

        card.add(top, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        btn.addActionListener(e -> {
            card.repaint();
            updateScenarios();
        });
        btn.addChangeListener(e -> card.repaint());

        return card;
    }
}
