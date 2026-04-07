package view;

import controller.AppController;
import model.QualityDimension;
import model.Metric;
import model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CollectPanel extends JPanel {
    private AppController controller;
    private JPanel contentPanel;

    public CollectPanel(AppController controller) {
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
        if (scenario == null) return;

        JLabel title = new JLabel("Step 4: Collect Data");
        title.setFont(UITheme.fontTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(4));

        JLabel subtitle = new JLabel("Raw values are shown for each metric. Scores (1–5) are automatically calculated.");
        subtitle.setFont(UITheme.fontBody());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(subtitle);
        contentPanel.add(Box.createVerticalStrut(4));

        JPanel formulaCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(245, 158, 11, 25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(UITheme.ACCENT);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
            }
        };
        formulaCard.setOpaque(false);
        formulaCard.setLayout(new GridLayout(2, 1, 0, 2));
        formulaCard.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        formulaCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        formulaCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel f1 = new JLabel("Higher is better:  score = 1 + (value − min) / (max − min) × 4");
        JLabel f2 = new JLabel("Lower is better:   score = 5 − (value − min) / (max − min) × 4   [rounded to nearest 0.5]");
        f1.setFont(UITheme.fontMono());
        f2.setFont(UITheme.fontMono());
        f1.setForeground(UITheme.ACCENT);
        f2.setForeground(UITheme.ACCENT);
        formulaCard.add(f1);
        formulaCard.add(f2);
        contentPanel.add(formulaCard);
        contentPanel.add(Box.createVerticalStrut(18));

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1–5)", "Coeff / Unit"};
        List<QualityDimension> dimensions = scenario.getDimensions();

        int totalRows = 0;
        for (QualityDimension d : dimensions) totalRows += d.getMetrics().size();

        Object[][] data = new Object[totalRows][6];
        int row = 0;
        for (QualityDimension d : dimensions) {
            for (Metric m : d.getMetrics()) {
                data[row][0] = m.getName();
                data[row][1] = m.getDirection().contains("Higher") ? "Higher ↑" : "Lower ↓";
                data[row][2] = (int) m.getRangeMin() + " – " + (int) m.getRangeMax();
                data[row][3] = formatValue(m.getValue(), m.getUnit());
                double score = m.calculateScore();
                data[row][4] = score;
                data[row][5] = m.getCoefficient() + " / " + m.getUnit();
                row++;
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = createStyledTable(model, totalRows);
        int tableH = table.getRowHeight() * totalRows + 26;
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(0, Math.min(tableH, 400)));
        tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, tableH));
        tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableScroll.setBorder(null);
        tableScroll.setBackground(UITheme.BG_MAIN);
        tableScroll.getViewport().setBackground(UITheme.BG_MAIN);
        contentPanel.add(tableScroll);
        contentPanel.add(Box.createVerticalStrut(24));

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setOpaque(false);
        navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("← Back");
        UITheme.styleButton(backBtn, UITheme.BG_INPUT, UITheme.TEXT_SECONDARY);
        backBtn.addActionListener(e -> controller.previousStep());

        JButton nextBtn = new JButton("Next: Analyse →");
        UITheme.styleButton(nextBtn, UITheme.SECONDARY, Color.WHITE);
        nextBtn.addActionListener(e -> {
            controller.markStepCompleted(3);
            controller.nextStep();
        });

        navPanel.add(backBtn);
        navPanel.add(nextBtn);
        contentPanel.add(navPanel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private String formatValue(double v, String unit) {
        if (v == Math.floor(v)) return String.valueOf((int) v);
        return String.valueOf(v);
    }

    private JTable createStyledTable(DefaultTableModel model, int rowCount) {
        JTable table = new JTable(model);
        table.setBackground(UITheme.TABLE_ROW);
        table.setForeground(UITheme.TEXT_PRIMARY);
        table.setFont(UITheme.fontBody());
        table.setRowHeight(32);
        table.setGridColor(UITheme.BORDER);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(59, 130, 246, 60));
        table.setSelectionForeground(UITheme.TEXT_PRIMARY);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.TABLE_HEADER);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.fontBold());
        header.setBorder(null);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                if (col == 4 && val != null) {
                    // Score column
                    double score = Double.parseDouble(val.toString());
                    Color scoreColor = getScoreColor(score);
                    setBackground(sel ? new Color(59, 130, 246, 60) : scoreColor);
                    setForeground(Color.WHITE);
                    setFont(UITheme.fontBold());
                    setHorizontalAlignment(SwingConstants.CENTER);
                    setText(String.format("%.1f", score));
                } else {
                    setBackground(sel ? new Color(59, 130, 246, 60) : (row % 2 == 0 ? UITheme.TABLE_ROW : UITheme.TABLE_ROW_ALT));
                    setForeground(UITheme.TEXT_PRIMARY);
                    setFont(UITheme.fontBody());
                    setHorizontalAlignment(SwingConstants.LEFT);
                }
                return this;
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(90);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);

        return table;
    }

    private Color getScoreColor(double score) {
        if (score >= 4.5) return new Color(21, 128, 61);
        if (score >= 3.5) return new Color(22, 163, 74);
        if (score >= 2.5) return new Color(202, 138, 4);
        if (score >= 1.5) return new Color(194, 65, 12);
        return new Color(185, 28, 28);
    }
}
