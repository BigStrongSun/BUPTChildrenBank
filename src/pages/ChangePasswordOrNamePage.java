package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Temp;
import pages.MainPage;
import pages.ModifyInformation;
import service.ChangePasswordService;
import service.ChangeNameService;
import service.TempService;
import util.GradientBackground;
import util.RoundedTextField;
import util.BtnOrange;

public class ChangePasswordOrNamePage extends JFrame {
    private TempService tempService = new TempService();
    private Temp temp = tempService.getTemp();

    public ChangePasswordOrNamePage() {
        setTitle("Change Password or Name");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        GradientBackground gradientBackground = new GradientBackground(colors, new float[]{0.0f, 0.2f, 0.8f, 1.0f});
        add(gradientBackground);
        gradientBackground.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 50, 1180, 620);
        gradientBackground.add(panel);

        initUI(panel);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(20, 20, 80, 30);
        backButton.addActionListener(e -> {
            dispose();
            if (temp.isParent()) {
                new ModifyInformation().setVisible(true); // If the user is a parent, return to ModifyInformation
            } else {
                new MainPage().setVisible(true); // If the user is not a parent, return to MainPage
            }
        });
        gradientBackground.add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI(JPanel panel) {
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(410, 120, 80, 25);
        panel.add(nameLabel);

        JLabel nameValueLabel = new JLabel(temp.getName());
        nameValueLabel.setBounds(500, 120, 165, 25);
        panel.add(nameValueLabel);

        JLabel oldPasswordLabel = new JLabel("Old Password:");
        oldPasswordLabel.setBounds(410, 160, 120, 25);
        panel.add(oldPasswordLabel);

        JPasswordField oldPasswordText = new JPasswordField(20);
        oldPasswordText.setBounds(530, 160, 200, 25);
        panel.add(oldPasswordText);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(410, 200, 120, 25);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordText = new JPasswordField(20);
        newPasswordText.setBounds(530, 200, 200, 25);
        panel.add(newPasswordText);

        BtnOrange changePasswordButton = new BtnOrange("Change Password");
        changePasswordButton.setBounds(410, 240, 180, 25);
        panel.add(changePasswordButton);
        changePasswordButton.addActionListener(e -> {
            String username = temp.isParent() ? String.valueOf(temp.getParentId()) : String.valueOf(temp.getChildId());
            String oldPassword = new String(oldPasswordText.getPassword());
            String newPassword = new String(newPasswordText.getPassword());
            if (ChangePasswordService.validatePassword(username, oldPassword)) {
                ChangePasswordService.changeUserPassword(username, newPassword);
                JOptionPane.showMessageDialog(null, "Password changed successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid old password");
            }
        });

        JLabel changeNameLabel = new JLabel("Enter new name:");
        changeNameLabel.setBounds(410, 350, 150, 25);
        panel.add(changeNameLabel);

        RoundedTextField newNameField = new RoundedTextField(20);
        newNameField.setBounds(560, 350, 200, 25);
        panel.add(newNameField);

        BtnOrange changeNameButton = new BtnOrange("Change Name");
        changeNameButton.setBounds(410, 390, 180, 25);
        panel.add(changeNameButton);
        changeNameButton.addActionListener(e -> {
            String newName = newNameField.getText();
            ChangeNameService.changeName(newName);
            JOptionPane.showMessageDialog(null, "Name changed to " + newName);
        });
    }

    public static void main(String[] args) {
        new ChangePasswordOrNamePage();
    }
}
