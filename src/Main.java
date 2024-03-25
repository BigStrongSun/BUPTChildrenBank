import pages.TaskPage;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskPage frame = new TaskPage();
            frame.setVisible(true);
        });
    }
}



