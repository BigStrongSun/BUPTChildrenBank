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
//在此页面，输入孩子id，点按钮，即可为家长换孩子。更改成功会显示“information updated";如果孩子用户不存在，会显示Child not found
public class ChangeNamePage extends JFrame{
    private JSONController jsonUser = new JSONController("user.txt");
    private List<User> userList = jsonUser.readArray(User.class);
    private JSONController jsonTemp = new JSONController("temp.txt");
    private Temp temp = (Temp) jsonTemp.read(Temp.class);
    private int currentId = ChangeNameService.findCurrentId();

    public ChangeNamePage(){
        setTitle("Change Name Page");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new FlowLayout());

        Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
        float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
        GradientBackground gradientBackground = new GradientBackground(colors, fractions);
        setContentPane(gradientBackground);



        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBounds(0, 0, 80, 30);
        add(backButton); // 直接添加到 JFrame
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 假设 MainPage 是另一个 JFrame 类
                setVisible(false); // 隐藏当前窗口
                dispose();
                new ModifyInformation().setVisible(true); // 显示主页面
            }
        });

        JLabel lbl = new JLabel("Please enter your new name here");
        RoundedTextField textField = new RoundedTextField(20);
        JButton changeNameButton = new JButton("Change Name");

        add(lbl);
        add(textField);
        add(changeNameButton);


        changeNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = textField.getText();
                ChangeNameService.changeName(newName);
                JOptionPane.showMessageDialog(null, "Name changed to " + newName);
            }

        });


        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        ChangeNamePage page = new ChangeNamePage();
    }
}
