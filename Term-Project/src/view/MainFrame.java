package view;

import controller.AppController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private AppController controller;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StepIndicator stepIndicator;

    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;

    public MainFrame(AppController controller) {
        this.controller = controller;
        setTitle("ISO/IEC 15939 — Software Measurement Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(UITheme.BG_MAIN);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);

        stepIndicator = new StepIndicator();

        JPanel centerArea = new JPanel(new BorderLayout());
        centerArea.setBackground(UITheme.BG_MAIN);
        centerArea.add(stepIndicator, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(UITheme.BG_MAIN);

        profilePanel = new ProfilePanel(controller);
        definePanel = new DefinePanel(controller);
        planPanel = new PlanPanel(controller);
        collectPanel = new CollectPanel(controller);
        analysePanel = new AnalysePanel(controller);

        cardPanel.add(profilePanel, "0");
        cardPanel.add(definePanel, "1");
        cardPanel.add(planPanel, "2");
        cardPanel.add(collectPanel, "3");
        cardPanel.add(analysePanel, "4");

        centerArea.add(cardPanel, BorderLayout.CENTER);
        add(centerArea, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(UITheme.PRIMARY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 58, 138), getWidth(), 0, new Color(17, 24, 89));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("ISO/IEC 15939 Software Measurement Tool");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(UITheme.fontTitle());

        JLabel subtitleLabel = new JLabel("Measurement Process Simulator");
        subtitleLabel.setForeground(new Color(147, 197, 253));
        subtitleLabel.setFont(UITheme.fontSmall());

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        header.add(titlePanel, BorderLayout.WEST);
        return header;
    }

    public void showStep(int step) {
        cardLayout.show(cardPanel, String.valueOf(step));
        stepIndicator.setCurrentStep(step);

        switch (step) {
            case 2: planPanel.refresh(); break;
            case 3: collectPanel.refresh(); break;
            case 4: analysePanel.refresh(); break;
        }
    }

    public void markStepCompleted(int step) {
        stepIndicator.markCompleted(step);
    }
}
