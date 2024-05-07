package service;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginService {

    public static void parentsaveUser(String username, String password, String identity) {
        File file = new File("user.txt");
        File file2 = new File ("currentuser.txt");
        BufferedWriter writer = null;
        BufferedWriter writer2 = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(username + ":" + password + ":" + identity);
            writer.newLine();
            
            writer2 = new BufferedWriter(new FileWriter(file2, false));
            writer2.write(username +  ":" + identity);
            writer2.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if (writer2 != null) {
                try {
                    writer2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean parentisUsernameExist(String username) {
        File file = new File("user.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    return true;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean parentvalidatePassword(String username, String password) {
        File file = new File("user.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    return user[1].equals(password);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    


    public static void childsaveUser(String username, String password, String identity) {
        File file = new File("user.txt");
        File file2 = new File("currentuser.txt");
        BufferedWriter writer = null;
        BufferedWriter writer2 = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(username + ":" + password + ":" + identity);
            writer.newLine();
            
            writer2 = new BufferedWriter(new FileWriter(file2, false));
            writer2.write(username + ":" + identity);
            writer2.newLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if (writer2 != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean childisUsernameExist(String username) {
        File file = new File("user.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    return true;
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean childvalidatePassword(String username, String password) {
        File file = new File("user.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    return user[1].equals(password);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    


}