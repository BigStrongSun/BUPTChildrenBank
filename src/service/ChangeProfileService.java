package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangeProfileService {
	public static boolean validatePassword(String username, String password) {
    	File file = new File("users.txt");
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
	public static void changeUsername(String username, String password, String newusername) {
    	File file = new File("users.txt");
        BufferedReader reader = null;
        File tempFile = new File("temp0.txt");
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(tempFile));
            String line = reader.readLine();
            while (line != null) {
                String[] user = line.split(":");
                if (user[0].equals(username)) {
                    writer.write(newusername + ":" + password + ":" + user[2]);
                } else {
                    writer.write(line);
                }
                writer.newLine();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file.delete();
        tempFile.renameTo(file);
    
        System.out.println("newusername for user " + username + " changed to " + newusername);
    }
}
