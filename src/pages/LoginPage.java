package pages;
import pages.ChildLoginPage;
import pages.ParentLoginPage;
import service.LoginService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginPage {
    static JFrame childFrame;
    static JFrame parentFrame;
    static JFrame frame;
	public static void main(String[] args) {
        frame = new JFrame("Identity Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel label = new JLabel("请选择您的身份:");
        panel.add(label);

        JButton parentButton = new JButton("父母");
        panel.add(parentButton);

        JButton childButton = new JButton("孩子");
        panel.add(childButton);

        parentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个新的JFrame对象，用于父母界面
                parentFrame = new JFrame("父母界面");
                parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                parentFrame.setSize(300, 200);

                // 在这里添加父母界面的内容
                JPanel panel = new JPanel();
                parentFrame.add(panel);

                ParentLoginPage.parentComponents(panel);

                frame.dispose();
                parentFrame.setVisible(true);
                
                
            }
        }); 
        
        

        childButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个新的JFrame对象，用于孩子界面
                childFrame = new JFrame("孩子界面");
                childFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                childFrame.setSize(300, 200);

                // 在这里添加孩子界面的内容
                JPanel panel = new JPanel();
                childFrame.add(panel);

                ChildLoginPage.childComponents(panel);

                frame.dispose();
                childFrame.setVisible(true);
                
                

            }
        });

        frame.setVisible(true);
    }

}
