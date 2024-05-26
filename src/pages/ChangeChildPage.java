package pages;

import domain.Temp;
import domain.User;
import util.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ChangeChildPage extends JFrame {
    private JSONController jsonUser = new JSONController("user.txt");
    private List<User> userList = jsonUser.readArray(User.class);
    private JSONController jsonTemp = new JSONController("temp.txt");
    private Temp temp = (Temp) jsonTemp.read(Temp.class);
    private int currentId = temp.getParentId();

    public ChangeChildPage() {
        setTitle("Modify Child");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        gradientBackground.setLayout(null);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(20, 20, 100, 30);
        gradientBackground.add(backButton);

        JLabel lblInfo = new JLabel();
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 30));

        gradientBackground.add(lblInfo);
        if (temp.getChildId() != 0) {
            lblInfo.setBounds(492, 160, 1100, 60);
            lblInfo.setText("Your child id is " + temp.getChildId());
        } else {
            lblInfo.setBounds(400, 160, 1100, 60);
            lblInfo.setText("You are not associated with any child currently.");
            JLabel lblInfo1 = new JLabel();
            lblInfo1.setText("Please enter the id of the child you want to be associated with, and click 'Add Child'.");
            lblInfo1.setFont(new Font("Arial", Font.PLAIN, 30));
            lblInfo1.setBounds(40, 200, 1100, 60);
            gradientBackground.add(lblInfo1);
        }

        RoundedTextField textField = new RoundedTextField(20);
        textField.setBounds(460, 280, 360, 30);
        gradientBackground.add(textField);

        BtnOrange changeChildButton = new BtnOrange(buttonText());
        changeChildButton.setBounds(460, 380, 150, 30);
        gradientBackground.add(changeChildButton);

        BtnOrange deleteAssociationButton = new BtnOrange("Delete Child");
        deleteAssociationButton.setBounds(660, 380, 150, 30);
        gradientBackground.add(deleteAssociationButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new MainPage().setVisible(true); // Assume MainPage is another JFrame class
            }
        });

        changeChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChildChangeAction(textField.getText());
            }
        });

        deleteAssociationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteChildAction();
            }
        });

        setContentPane(gradientBackground);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void handleChildChangeAction(String childId) {
        // Implementation for changing the child
        boolean exist = false;
        if (temp.getChildId() == 0) {
            for (User user : userList) {
                if (user.getUsername().equals(childId) && user.getIdentity().equals("child") && user.getChildOrParentId() == 0) {
                    ChangeChildService.changeChild(currentId, parseInt(childId));
                    temp.setChildId(parseInt(childId));
                    temp.setParent(true);
                    WriteToTemp.writeToTempFile(parseInt(childId), temp.getName(), true, currentId);
                    JOptionPane.showMessageDialog(null, "Information updated");
                    exist = true;
                    dispose();
                    new ChangeChildPage().setVisible(true);
                    break;
                }
            }
            if (!exist) {
                JOptionPane.showMessageDialog(null, "Child not found or not available");
            }
        } else {
            JOptionPane.showMessageDialog(null, "You already have a child");
        }
    }

    private void handleDeleteChildAction() {
        // Implementation for deleting the child
        if (temp.getChildId() != 0) {
            ChangeChildService.clearAssociation(temp.getParentId(), temp.getChildId());
            WriteToTemp.writeToTempFile(0, temp.getName(), true, currentId);
            JOptionPane.showMessageDialog(null, "Child cleared");
            dispose();
            new ChangeChildPage().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "You have no child to clear");
        }
    }

    public String buttonText() {
        if (temp.getChildId() == 0) {
            return "Add Child";
        } else {
            return "Change Child";
        }
    }

    public static void main(String[] args) {
        new ChangeChildPage();
    }
}
