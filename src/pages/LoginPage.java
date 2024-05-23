package pages;
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
    static LoginPageForAll page;
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
                page = new LoginPageForAll(true);

                frame.dispose();
                
                
            }
        }); 
        
        

        childButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建一个新的JFrame对象，用于孩子界面


                page = new LoginPageForAll(false);

                frame.dispose();

                
                

            }
        });

        frame.setVisible(true);
    }

}
