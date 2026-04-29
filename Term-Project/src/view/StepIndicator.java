package view;

import javax.swing.*;
import java.awt.*;

public class StepIndicator extends JPanel {
    private static final String[] STEP_NAMES = { "Profile", "Define", "Plan", "Collect", "Analyse" };
    private int currentStep;
    private boolean[] completed;

    public StepIndicator() {
        this.currentStep = 0;
        this.completed = new boolean[5];
        setBackground(UITheme.BG_CARD);
        setPreferredSize(new Dimension(0, 70));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER));
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        repaint();
    }

    public void markCompleted(int step) {
        if (step >= 0 && step < completed.length) {
            completed[step] = true;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int n = STEP_NAMES.length;
        int stepW = w / n;
        int circleR = 16;
        int cy = h / 2 - 8;

        for (int i = 0; i < n; i++) {
            int cx = stepW * i + stepW / 2;

            if (i < n - 1) {
                int nextCx = stepW * (i + 1) + stepW / 2;
                g2.setStroke(new BasicStroke(2f));
                if (completed[i]) {
                    g2.setColor(UITheme.STEP_DONE);
                } else {
                    g2.setColor(UITheme.STEP_INACTIVE);
                }
                g2.drawLine(cx + circleR, cy, nextCx - circleR, cy);
            }

            Color circleColor;
            if (i == currentStep) {
                circleColor = UITheme.STEP_ACTIVE;
            } else if (completed[i]) {
                circleColor = UITheme.STEP_DONE;
            } else {
                circleColor = UITheme.STEP_INACTIVE;
            }

            g2.setColor(circleColor);
            g2.fillOval(cx - circleR, cy - circleR, circleR * 2, circleR * 2);

            g2.setColor(Color.WHITE);
            g2.setFont(UITheme.fontBold().deriveFont(12f));

            if (completed[i] && i != currentStep) {
                g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int bx = cx - 5;
                int by = cy;
                g2.drawLine(bx, by + 2, bx + 3, by + 6);
                g2.drawLine(bx + 3, by + 6, bx + 8, by - 2);
            } else {
                FontMetrics fm = g2.getFontMetrics();
                String num = String.valueOf(i + 1);
                g2.drawString(num, cx - fm.stringWidth(num) / 2, cy + fm.getAscent() / 2 - 1);
            }

            g2.setFont(i == currentStep ? UITheme.fontBold().deriveFont(11f) : UITheme.fontSmall());
            g2.setColor(i == currentStep ? UITheme.TEXT_PRIMARY : UITheme.TEXT_SECONDARY);
            FontMetrics fm2 = g2.getFontMetrics();
            String lbl = STEP_NAMES[i];
            g2.drawString(lbl, cx - fm2.stringWidth(lbl) / 2, cy + circleR + 16);
        }

        g2.dispose();
    }
}
