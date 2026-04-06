package view;

import controller.AppController;
import model.QualityDimension;
import model.Metric;
import model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PlanPanel extends JPanel {
    private AppController controller;
    private JPanel contentPanel;

    public PlanPanel(AppController controller) {
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

        JLabel title = new JLabel("Step 3: Plan Measurement");
        title.setFont(UITheme.fontTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(4));

        JLabel subtitle = new JLabel("Scenario: " + scenario.getName() + " — Quality dimensions and metrics (read-only)");
        subtitle.setFont(UITheme.fontBody());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(subtitle);
        contentPanel.add(Box.createVerticalStrut(20));

        List<QualityDimension> dimensions = scenario.getDimensions();
        for (QualityDimension dim : dimensions) {
            // Dimension header
            JPanel dimHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UITheme.PRIMARY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
            };
            dimHeader.setOpaque(false);
            dimHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            dimHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dimLabel = new JLabel(dim.getName() + "   (Coefficient: " + dim.getCoefficient() + ")");
            dimLabel.setFont(UITheme.fontBold());
            dimLabel.setForeground(Color.WHITE);
            dimHeader.add(dimLabel);
            contentPanel.add(dimHeader);

            // Metrics table
            String[] columns = {"Metric", "Coefficient", "Direction", "Range", "Unit"};
            List<Metric> metrics = dim.getMetrics();
            Object[][] data = new Object[metrics.size()][5];
            for (int i = 0; i < metrics.size(); i++) {
                Metric m = metrics.get(i);
                data[i][0] = m.getName();
                data[i][1] = m.getCoefficient();
                data[i][2] = m.getDirection().contains("Higher") ? "Higher ↑" : "Lower ↓";
                data[i][3] = (int) m.getRangeMin() + " – " + (int) m.getRangeMax();
                data[i][4] = m.getUnit();
            }

            DefaultTableModel model = new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int col) { return false; }
            };
            JTable table = createStyledTable(model);
            JScrollPane tableScroll = new JScrollPane(table);
            tableScroll.setPreferredSize(new Dimension(0, table.getRowHeight() * metrics.size() + 26));
            tableScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, table.getRowHeight() * metrics.size() + 26));
            tableScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            tableScroll.setBorder(null);
            tableScroll.setBackground(UITheme.BG_MAIN);
            tableScroll.getViewport().setBackground(UITheme.BG_MAIN);
            contentPanel.add(tableScroll);
            contentPanel.add(Box.createVerticalStrut(16));
        }

        // Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setOpaque(false);
        navPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton backBtn = new JButton("← Back");
        UITheme.styleButton(backBtn, UITheme.BG_INPUT, UITheme.TEXT_SECONDARY);
        backBtn.addActionListener(e -> controller.previousStep());

        JButton nextBtn = new JButton("Next: Collect Data →");
        UITheme.styleButton(nextBtn, UITheme.PRIMARY_LIGHT, Color.WHITE);
        nextBtn.addActionListener(e -> {
            controller.markStepCompleted(2);
            controller.nextStep();
        });

        navPanel.add(backBtn);
        navPanel.add(nextBtn);
        contentPanel.add(navPanel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(UITheme.TABLE_ROW);
        table.setForeground(UITheme.TEXT_PRIMARY);
        table.setFont(UITheme.fontBody());
        table.setRowHeight(30);
        table.setGridColor(UITheme.BORDER);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(59, 130, 246, 60));
        table.setSelectionForeground(UITheme.TEXT_PRIMARY);
        table.setIntercellSpacing(new Dimension(0, 0));

        // Header
        JTableHeader header = table.getTableHeader();
        header.setBackground(UITheme.TABLE_HEADER);
        header.setForeground(Color.WHITE);
        header.setFont(UITheme.fontBold());
        header.setBorder(null);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean focus, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                setBackground(sel ? new Color(59, 130, 246, 60) : (row % 2 == 0 ? UITheme.TABLE_ROW : UITheme.TABLE_ROW_ALT));
                setForeground(UITheme.TEXT_PRIMARY);
                setFont(UITheme.fontBody());
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(160);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);

        return table;
    }
}
