import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel {

    private JTextField txtProjectName;
    private JTextField txtTeamLeader;
    private JTextField txtStartDate;

    private JComboBox<String> cmbTeamSize;
    private JComboBox<String> cmbProjectType;

    private JButton btnSave;
    private JButton btnClear;

    public ProjectFormPanel() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblProjectName = new JLabel("Project Name:");
        txtProjectName = new JTextField(15);

        JLabel lblTeamLeader = new JLabel("Team Leader:");
        txtTeamLeader = new JTextField(15);

        JLabel lblTeamSize = new JLabel("Team Size:");
        cmbTeamSize = new JComboBox<>(new String[]{
                "Select",
                "1-3",
                "4-6",
                "7-10",
                "10+"
        });

        JLabel lblProjectType = new JLabel("Project Type:");
        cmbProjectType = new JComboBox<>(new String[]{
                "Select",
                "Web",
                "Mobile",
                "Desktop",
                "API"
        });

        JLabel lblStartDate = new JLabel("Start Date (DD/MM/YYYY):");
        txtStartDate = new JTextField(15);

        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");

        gbc.gridx = 0; gbc.gridy = 0;
        add(lblProjectName, gbc);
        gbc.gridx = 1;
        add(txtProjectName, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(lblTeamLeader, gbc);
        gbc.gridx = 1;
        add(txtTeamLeader, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(lblTeamSize, gbc);
        gbc.gridx = 1;
        add(cmbTeamSize, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(lblProjectType, gbc);
        gbc.gridx = 1;
        add(cmbProjectType, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(lblStartDate, gbc);
        gbc.gridx = 1;
        add(txtStartDate, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(btnSave, gbc);
        gbc.gridx = 1;
        add(btnClear, gbc);

        btnSave.addActionListener(e -> saveProject());
        btnClear.addActionListener(e -> clearForm());
    }

    private void saveProject() {

        String projectName = txtProjectName.getText().trim();
        String teamLeader = txtTeamLeader.getText().trim();
        String startDate = txtStartDate.getText().trim();
        String teamSize = (String) cmbTeamSize.getSelectedItem();
        String projectType = (String) cmbProjectType.getSelectedItem();

        if (projectName.isEmpty() ||
            teamLeader.isEmpty() ||
            startDate.isEmpty() ||
            teamSize.equals("Select") ||
            projectType.equals("Select")) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String recordTime = now.format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("projects.txt", true))) {

            writer.write("---- Project Entry ----");
            writer.newLine();
            writer.write("Project Name : " + projectName);
            writer.newLine();
            writer.write("Team Leader  : " + teamLeader);
            writer.newLine();
            writer.write("Team Size    : " + teamSize);
            writer.newLine();
            writer.write("Project Type : " + projectType);
            writer.newLine();
            writer.write("Start Date   : " + startDate);
            writer.newLine();
            writer.write("Record Time  : " + recordTime);
            writer.newLine();
            writer.write("========================");
            writer.newLine();
            writer.newLine();

            JOptionPane.showMessageDialog(this,
                    "Project saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error writing to file!",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtProjectName.setText("");
        txtTeamLeader.setText("");
        txtStartDate.setText("");
        cmbTeamSize.setSelectedIndex(0);
        cmbProjectType.setSelectedIndex(0);
    }
}