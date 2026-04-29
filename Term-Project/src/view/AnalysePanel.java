package view;

import controller.AppController;
import model.QualityDimension;
import model.Scenario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalysePanel extends JPanel {
    private AppController controller;
    private JPanel contentPanel;

    public AnalysePanel(AppController controller) {
        this.controller = controller;
        setBackground(UITheme.BG_MAIN);
        setLayout(new BorderLayout());
        initLayout();
    }

    private void initLayout() {
        contentPanel = new JPanel();
        contentPanel.setBackground(UITheme.BG_MAIN);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(UITheme.BG_MAIN);
        scrollPane.getViewport().setBackground(UITheme.BG_MAIN);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        contentPanel.removeAll();

        Scenario scenario = controller.getSession().getSelectedScenario();
        if (scenario == null)
            return;

        JLabel title = new JLabel("Step 5: Analyse Results");
        title.setFont(UITheme.fontTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(4));

        JLabel subtitle = new JLabel("Session: " + controller.getSession().getSessionName() +
                " | User: " + controller.getSession().getUsername() +
                " | Scenario: " + scenario.getName());
        subtitle.setFont(UITheme.fontSmall());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(subtitle);
        contentPanel.add(Box.createVerticalStrut(20));

        List<QualityDimension> dimensions = scenario.getDimensions();

        JLabel secA = sectionLabel("5a. Dimension-Based Weighted Averages");
        contentPanel.add(secA);
        contentPanel.add(Box.createVerticalStrut(10));

        QualityDimension lowestDim = null;
        double lowestScore = Double.MAX_VALUE;

        for (QualityDimension dim : dimensions) {
            double score = dim.calculateDimensionScore();
            if (score < lowestScore) {
                lowestScore = score;
                lowestDim = dim;
            }
            contentPanel.add(buildDimensionBar(dim, score));
            contentPanel.add(Box.createVerticalStrut(8));
        }
        contentPanel.add(Box.createVerticalStrut(16));

        JLabel secB = sectionLabel("5b. Radar Chart (Bonus)");
        contentPanel.add(secB);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel radarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(UITheme.BORDER);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        radarContainer.setOpaque(false);
        radarContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        radarContainer.setMaximumSize(new Dimension(360, 320));
        radarContainer.setPreferredSize(new Dimension(360, 320));

        RadarChart radarChart = new RadarChart(dimensions);
        radarContainer.add(radarChart);
        contentPanel.add(radarContainer);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel secC = sectionLabel("5c. Gap Analysis");
        contentPanel.add(secC);
        contentPanel.add(Box.createVerticalStrut(10));

        if (lowestDim != null) {
            contentPanel.add(buildGapAnalysis(lowestDim, lowestScore));
        }
        contentPanel.add(Box.createVerticalStrut(24));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setOpaque(false);
        navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("← Back");
        UITheme.styleButton(backBtn, UITheme.BG_INPUT, UITheme.TEXT_SECONDARY);
        backBtn.addActionListener(e -> controller.previousStep());

        JButton restartBtn = new JButton("↺ Start New Session");
        UITheme.styleButton(restartBtn, UITheme.ACCENT, new Color(30, 20, 0));
        restartBtn.addActionListener(e -> controller.goToStep(0));

        navPanel.add(backBtn);
        navPanel.add(restartBtn);
        contentPanel.add(navPanel);

        controller.markStepCompleted(4);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(UITheme.fontSubtitle());
        lbl.setForeground(UITheme.PRIMARY_LIGHT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JPanel buildDimensionBar(QualityDimension dim, double score) {
        JPanel row = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
            }
        };
        row.setOpaque(false);
        row.setLayout(new BorderLayout(12, 0));
        row.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        labelPanel.setOpaque(false);
        labelPanel.setPreferredSize(new Dimension(180, 40));

        JLabel nameLabel = new JLabel(dim.getName());
        nameLabel.setFont(UITheme.fontBold());
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);

        JLabel coeffLabel = new JLabel("Coefficient: " + dim.getCoefficient());
        coeffLabel.setFont(UITheme.fontSmall());
        coeffLabel.setForeground(UITheme.TEXT_SECONDARY);

        labelPanel.add(nameLabel);
        labelPanel.add(coeffLabel);

        JPanel barPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int h = getHeight();
                int barH = 14;
                int barY = (h - barH) / 2;
                int totalW = getWidth();

                g2.setColor(UITheme.BG_INPUT);
                g2.fillRoundRect(0, barY, totalW, barH, barH, barH);

                double ratio = score / 5.0;
                int fillW = (int) (totalW * ratio);
                Color barColor = getScoreColor(score);
                g2.setColor(barColor);
                if (fillW > barH) {
                    g2.fillRoundRect(0, barY, fillW, barH, barH, barH);
                }
                g2.dispose();
            }
        };
        barPanel.setOpaque(false);

        JLabel scoreLabel = new JLabel(String.format("%.2f", score) + " / 5.0");
        scoreLabel.setFont(UITheme.fontBold());
        scoreLabel.setForeground(getScoreColor(score));
        scoreLabel.setPreferredSize(new Dimension(80, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(labelPanel, BorderLayout.WEST);
        row.add(barPanel, BorderLayout.CENTER);
        row.add(scoreLabel, BorderLayout.EAST);

        return row;
    }

    private JPanel buildGapAnalysis(QualityDimension dim, double score) {
        double gap = 5.0 - score;
        String qualityLevel;
        Color levelColor;
        if (score >= 4.5) {
            qualityLevel = "Excellent";
            levelColor = UITheme.SUCCESS;
        } else if (score >= 3.5) {
            qualityLevel = "Good";
            levelColor = new Color(34, 197, 94);
        } else if (score >= 2.5) {
            qualityLevel = "Needs Improvement";
            levelColor = UITheme.ACCENT;
        } else {
            qualityLevel = "Poor";
            levelColor = UITheme.DANGER;
        }

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(239, 68, 68, 20));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(UITheme.DANGER);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel header = new JLabel("⚠  Lowest Scoring Dimension");
        header.setFont(UITheme.fontBold());
        header.setForeground(UITheme.DANGER);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(header);
        card.add(Box.createVerticalStrut(12));

        JPanel grid = new JPanel(new GridLayout(4, 2, 10, 6));
        grid.setOpaque(false);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        addRow(grid, "Dimension:", dim.getName());
        addRow(grid, "Score:", String.format("%.2f", score));
        addRow(grid, "Gap (5.0 − score):", String.format("%.2f", gap));
        addRow(grid, "Quality Level:", qualityLevel, levelColor);

        card.add(grid);
        card.add(Box.createVerticalStrut(12));

        JLabel note = new JLabel("This dimension has the lowest score and requires the most improvement.");
        note.setFont(UITheme.fontBody());
        note.setForeground(UITheme.TEXT_SECONDARY);
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(note);

        return card;
    }

    private void addRow(JPanel grid, String labelText, String valueText) {
        addRow(grid, labelText, valueText, UITheme.TEXT_PRIMARY);
    }

    private void addRow(JPanel grid, String labelText, String valueText, Color valueColor) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(UITheme.fontBold());
        lbl.setForeground(UITheme.TEXT_SECONDARY);

        JLabel val = new JLabel(valueText);
        val.setFont(UITheme.fontBold());
        val.setForeground(valueColor);

        grid.add(lbl);
        grid.add(val);
    }

    private Color getScoreColor(double score) {
        if (score >= 4.5)
            return new Color(21, 128, 61);
        if (score >= 3.5)
            return new Color(22, 163, 74);
        if (score >= 2.5)
            return UITheme.ACCENT;
        if (score >= 1.5)
            return new Color(194, 65, 12);
        return UITheme.DANGER;
    }
}
