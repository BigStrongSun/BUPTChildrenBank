package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import service.LoginService;
import service.TempService;
import util.*;

public class LoginPage {
	

	static JFrame childFrame;
	static JFrame parentFrame;
	public static JFrame frame;
	static LoginPageForAll page;
	static LoginPage LoginPage;
	public static void main(String[] args) {
		LoginPage LoginPage = new LoginPage();
	}
	public LoginPage() {
        frame = new JFrame("Identity selection");
		LoginPage = this;
        TempService tempService = new TempService();
        tempService.clearTemp(); // 清空temp.txt文件内容
//    	frame.setTitle("Identity selection");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1280, 720);
    	frame.setLocationRelativeTo(null);
    	frame.setLayout(null);
		Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
		float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
		GradientBackground gradientBackground = new GradientBackground(colors, fractions);
		frame.setContentPane(gradientBackground);
		
		gradientBackground.setLayout(null);

        JLabel titleLabel = new JLabel("Identity selection");
        titleLabel.setBounds(420,30,600,70);
        titleLabel.setFont(new Font("Times New Roman",Font.BOLD,60));
        gradientBackground.add(titleLabel);


        JLabel label = new JLabel("Please select your identity");
        label.setBounds(420, 250, 600, 80);
        label.setFont(new Font("Times New Roman",Font.BOLD,40));
        gradientBackground.add(label);


        JButton parentButton = new BtnPink("Parent");
        parentButton.setBounds(370, 350, 250, 130);
        parentButton.setFont(new Font("Times New Roman",Font.BOLD,32));
        gradientBackground.add(parentButton);

        JButton childButton = new BtnBlue("Child");
        childButton.setFont(new Font("Times New Roman",Font.BOLD,32));
        childButton.setBounds(670, 350, 250, 130);
        gradientBackground.add(childButton);

        
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
    private static String getJarDir() throws URISyntaxException {
        String jarPath = LoginPage.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return new File(jarPath).getParent();
    }
}
