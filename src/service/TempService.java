package service;

import domain.Temp;
import domain.Wish;
import util.JSONController;

import javax.swing.*;
import java.util.Iterator;

public class TempService {
    private Temp temp;
    private JSONController json = new JSONController("temp.txt");

    public TempService() {
        temp = (Temp) json.read(Temp.class);
    }

    public Temp getTemp() {
        return this.temp;
    }

    public Temp setTemp(Temp temp) {
        this.temp = temp;
        return temp;
    }

    public void deleteTemp() {
        // 检查任务列表是否为空
        if (temp == null) {
            System.out.println("temp file is empty");
            return;
        }
        Temp temp1 = new Temp();
        json.write(temp1); // 更新任务列表到文件中
        System.out.println("logout successfully！");
        JOptionPane.showMessageDialog(null, "logout successfully！", "mention", JOptionPane.INFORMATION_MESSAGE);
    }

}
