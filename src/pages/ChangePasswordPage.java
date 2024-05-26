package pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import domain.Temp;
import service.ChangePasswordService;
import service.TempService;

public class ChangePasswordPage  extends JFrame{

    public static TempService tempService = new TempService();
    public static Temp temp = tempService.getTemp();

    public static void main(String[] args) {
        new ChangePasswordPage();
    }

    public ChangePasswordPage(){
        setTitle("Change Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        add(panel);

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
                String username;
                if(temp.isParent()){
                    username = String.valueOf(temp.getParentId());
                } else{
                    username = String.valueOf(temp.getChildId());
                }
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

        setVisible(true);
    }



    
}
