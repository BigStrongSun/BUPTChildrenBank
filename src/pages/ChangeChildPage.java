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
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        RoundedTextField textField = new RoundedTextField(20);
        JButton changeChildButton = new JButton("Change Child");
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
                ChangeChildService.clearAssociation(temp.getParentId(), temp.getChildId());
                WriteToTemp.writeToTempFile(0, temp.getName(), true,currentId);
                JOptionPane.showMessageDialog(null, "Child cleared");
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        ChangeChildPage page = new ChangeChildPage();
    }
}
