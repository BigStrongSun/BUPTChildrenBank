package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Temp;
import service.ChangePasswordService;
import service.TempService;

public class ChangePasswordPage extends JFrame {
    private static TempService tempService = new TempService();
    private static Temp temp = tempService.getTemp();
    private JFrame frame;

    public ChangePasswordPage() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Change Password");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Name:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JLabel userLabel2 = new JLabel(temp.getName());
        userLabel2.setBounds(100, 20, 165, 25);
        panel.add(userLabel2);

        JLabel passwordLabel = new JLabel("Old Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(newPasswordLabel);

        JPasswordField newPasswordText = new JPasswordField(20);
        newPasswordText.setBounds(100, 80, 165, 25);
        panel.add(newPasswordText);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(10, 110, 165, 25);
        panel.add(changePasswordButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = temp.isParent() ? String.valueOf(temp.getParentId()) : String.valueOf(temp.getChildId());
                String oldPassword = new String(passwordText.getPassword());
                String newPassword = new String(newPasswordText.getPassword());

                if (ChangePasswordService.validatePassword(username, oldPassword)) {
                    ChangePasswordService.changeUserPassword(username, newPassword);
                    JOptionPane.showMessageDialog(null, "Password changed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid old password");
                }
            }
        });
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ChangePasswordPage passwordPage = new ChangePasswordPage();
            passwordPage.setVisible(true);
        });
    }
}
