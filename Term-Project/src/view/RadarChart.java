package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.QualityDimension;

public class RadarChart extends JPanel {
    private List<QualityDimension> dimensions;
    private double[] scores;
    private String[] labels;

    public RadarChart(List<QualityDimension> dimensions) {
        this.dimensions = dimensions;
        setBackground(UITheme.BG_CARD);
        setPreferredSize(new Dimension(340, 300));
        updateData();
    }

    private void updateData() {
        if (dimensions == null || dimensions.isEmpty()) return;
        int n = dimensions.size();
        scores = new double[n];
        labels = new String[n];
        for (int i = 0; i < n; i++) {
            scores[i] = dimensions.get(i).calculateDimensionScore();
            labels[i] = dimensions.get(i).getName();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scores == null || scores.length == 0) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        int radius = Math.min(w, h) / 2 - 50;
        int n = scores.length;
        double maxScore = 5.0;

        // Draw grid rings
        int rings = 5;
        for (int r = 1; r <= rings; r++) {
            double ringRadius = radius * r / rings;
            Polygon poly = getPolygon(cx, cy, ringRadius, n);
            g2.setStroke(new BasicStroke(0.8f));
            g2.setColor(new Color(100, 116, 139, 80));
            g2.drawPolygon(poly);
            // Ring label
            if (r < rings) {
                g2.setFont(UITheme.fontSmall().deriveFont(9f));
                g2.setColor(UITheme.TEXT_MUTED);
                g2.drawString(String.valueOf(r), cx + 4, (int)(cy - ringRadius) + 4);
            }
        }

        // Draw axes
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(100, 116, 139, 120));
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(270 + 360.0 * i / n);
            int ax = (int)(cx + radius * Math.cos(angle));
            int ay = (int)(cy + radius * Math.sin(angle));
            g2.drawLine(cx, cy, ax, ay);
        }

        // Draw data polygon (filled)
        int[] dataX = new int[n];
        int[] dataY = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(270 + 360.0 * i / n);
            double r2 = radius * scores[i] / maxScore;
            dataX[i] = (int)(cx + r2 * Math.cos(angle));
            dataY[i] = (int)(cy + r2 * Math.sin(angle));
        }

        // Filled polygon
        Polygon dataPoly = new Polygon(dataX, dataY, n);
        g2.setColor(new Color(59, 130, 246, 60));
        g2.fillPolygon(dataPoly);
        g2.setColor(new Color(59, 130, 246, 200));
        g2.setStroke(new BasicStroke(2f));
        g2.drawPolygon(dataPoly);

        // Draw data points
        for (int i = 0; i < n; i++) {
            g2.setColor(UITheme.PRIMARY_LIGHT);
            g2.fillOval(dataX[i] - 4, dataY[i] - 4, 8, 8);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(dataX[i] - 4, dataY[i] - 4, 8, 8);
        }

        // Draw labels
        g2.setFont(UITheme.fontSmall().deriveFont(10f));
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(270 + 360.0 * i / n);
            double labelR = radius + 28;
            int lx = (int)(cx + labelR * Math.cos(angle));
            int ly = (int)(cy + labelR * Math.sin(angle));

            String label = labels[i];
            String scoreStr = String.format("%.2f", scores[i]);
            FontMetrics fm = g2.getFontMetrics();
            int lw = fm.stringWidth(label);

            // Adjust for quadrant
            if (Math.cos(angle) < -0.1) lx -= lw;
            else if (Math.abs(Math.cos(angle)) <= 0.1) lx -= lw / 2;

            g2.setColor(UITheme.TEXT_PRIMARY);
            g2.drawString(label, lx, ly);
            g2.setColor(UITheme.ACCENT);
            g2.setFont(UITheme.fontSmall().deriveFont(Font.BOLD, 9f));
            g2.drawString(scoreStr, lx, ly + 11);
            g2.setFont(UITheme.fontSmall().deriveFont(10f));
        }

        // Title
        g2.setFont(UITheme.fontBold().deriveFont(11f));
        g2.setColor(UITheme.TEXT_SECONDARY);
        FontMetrics fm = g2.getFontMetrics();
        String chartTitle = "Radar Chart — Dimension Scores";
        g2.drawString(chartTitle, cx - fm.stringWidth(chartTitle) / 2, 16);

        g2.dispose();
    }

    private Polygon getPolygon(int cx, int cy, double radius, int n) {
        int[] xs = new int[n];
        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = Math.toRadians(270 + 360.0 * i / n);
            xs[i] = (int)(cx + radius * Math.cos(angle));
            ys[i] = (int)(cy + radius * Math.sin(angle));
        }
        return new Polygon(xs, ys, n);
    }
}
