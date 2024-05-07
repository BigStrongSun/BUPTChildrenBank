package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangePasswordService {
	public static void changeUserPassword(String username, String newPassword) {
    	File file = new File("user.txt");
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
                    writer.write(username + ":" + newPassword + ":" + user[2]);
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
    
        System.out.println("Password for user " + username + " changed to " + newPassword);
    }
}
