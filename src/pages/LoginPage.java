package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.*;

public class LoginPage {
	

	static JFrame childFrame;
	static JFrame parentFrame;
	static JFrame frame;
	static LoginPageForAll page;

	public static void main(String[] args) {
		
    	
    	frame = new JFrame("Identity selection");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(600, 400);
    	frame.setLocationRelativeTo(null);

    	GradientPanel titlePanel = new GradientPanel(new Color(200, 200, 200), new Color(150, 150, 150));
    	frame.add(titlePanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Identity selection");
        titleLabel.setPreferredSize(new Dimension(150, 50));
        titleLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        GradientPanel contentPanel = new GradientPanel(new Color(200, 200, 200), new Color(255, 255, 255));
        frame.add(contentPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Please select your identity:");
        label.setPreferredSize(new Dimension(200, 30));
        label.setFont(new Font("Times New Roman",Font.BOLD,16));
        contentPanel.add(label);

        JButton parentButton = new JButton("Parent");
        parentButton.setPreferredSize(new Dimension(100, 30));
        contentPanel.add(parentButton);

        JButton childButton = new JButton("Child");
        childButton.setPreferredSize(new Dimension(100, 30));
        contentPanel.add(childButton);

        parentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPageForAll(true);
                frame.dispose();
            }
        });

        childButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPageForAll(false);
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
