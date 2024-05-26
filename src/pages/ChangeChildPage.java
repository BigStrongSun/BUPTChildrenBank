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
public class ChangeChildPage extends JFrame{
    private JSONController jsonUser = new JSONController("user.txt");
    private List<User> userList = jsonUser.readArray(User.class);
    private JSONController jsonTemp = new JSONController("temp.txt");
    private Temp temp = (Temp) jsonTemp.read(Temp.class);
    private int currentId = temp.getParentId();

    public ChangeChildPage(){
        setTitle("Modify Child");
        setSize(1280, 720);
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

        JLabel lbl1 = new JLabel("Your child id is " + temp.getChildId());
        JLabel lbl2 = new JLabel("You are not associated with any child currently. Please enter the id of the child you want to be associated with, and click 'Add Child'.");


        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 假设 MainPage 是另一个 JFrame 类
                setVisible(false); // 隐藏当前窗口
                new MainPage().setVisible(true); // 显示主页面
            }
        });

        RoundedTextField textField = new RoundedTextField(20);
        JButton changeChildButton = new JButton(buttonText());
        JButton deleteAssociationButton = new JButton("Delete Child");
        //changeChildButton.setBounds(10, 110, 165, 25);
        add(textField);
        add(changeChildButton);
        add(deleteAssociationButton);//这个按钮直接点即可，与输入内容无关

        changeChildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String childId = textField.getText();
                boolean exist = false;

                if (temp.getChildId() == 0) {//没有绑定孩子的时候才能绑定新孩子
                    for(User user: userList){
                        //检测用户存在且为孩子，且孩子尚未绑定家长
                        if (user.getUsername().equals(childId)
                                && user.getIdentity().equals("child")
                                && (user.getChildOrParentId() == 0)) {
                            ChangeChildService.changeChild(currentId, Integer.parseInt(childId));
                            temp.setChildId(Integer.parseInt(childId));
                            temp.setParent(true);
                            //jsonTemp.write(temp);//更新temp中的信息
                            WriteToTemp.writeToTempFile(Integer.parseInt(childId), temp.getName(), true,currentId);
                            JOptionPane.showMessageDialog(null, "Information updated");
                            exist = true;
                            break;

                        }

                    }
                    //Back to main page when successfully associated with a child
                    dispose();
                    new ChangeChildPage();

                    if(!exist){
                        JOptionPane.showMessageDialog(null, "Child not found or not available");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "You already have a child");
                }
            }
        });

        deleteAssociationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (temp.getChildId() != 0) {
                    ChangeChildService.clearAssociation(temp.getParentId(), temp.getChildId());
                    WriteToTemp.writeToTempFile(0, temp.getName(), true,currentId);
                    JOptionPane.showMessageDialog(null, "Child cleared");
                    //back to main page when cleared a child
                    dispose();
                    new ChangeChildPage();
                } else {
                    JOptionPane.showMessageDialog(null, "You have no child to clear");
                }
            }
        });
        if(temp.getChildId() != 0){
            //lbl1.setBounds(100,100,500,100);//目前还是FlowLayout，最好改一下
            add(lbl1);
        }else{
            add(lbl2);
        }
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public String buttonText(){
        if(temp.getChildId() == 0){
            System.out.println("no child");
            return "Add Child";
        }else{
            return "Change Child";
        }
    }

    public static void main(String[] args) {
        new ChangeChildPage();
    }
}
