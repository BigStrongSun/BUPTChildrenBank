package pages;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import domain.Temp;
import service.ChangeNameService;
import service.ChangePasswordService;
import service.TempService;
import util.RoundedTextField;

public class ChangePasswordOrNamePage extends JFrame{

    public TempService tempService = new TempService();
    public Temp temp = tempService.getTemp();

    public static void main(String[] args) {
        new ChangePasswordOrNamePage();
    }

    public ChangePasswordOrNamePage(){
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
                    dispose();
                    new ChangePasswordOrNamePage();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid old password");
                }
            }
        });

        JLabel lbl = new JLabel("Please enter your new name here");
        RoundedTextField textField2 = new RoundedTextField(20);
        JButton changeNameButton = new JButton("Change Name");

        lbl.setBounds(10,200,500,10);
        textField2.setBounds(10,230,200,20);
        changeNameButton.setBounds(230,230,150,20);
        panel.add(lbl);
        panel.add(textField2);
        panel.add(changeNameButton);


        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = textField2.getText();
                ChangeNameService.changeName(newName);
                JOptionPane.showMessageDialog(null, "Name changed to " + newName);
                dispose();
                new ChangePasswordOrNamePage();
            }

        });

        setVisible(true);
    }



    
}
