package service;

import domain.Temp;
import util.JSONController;

public class TempService {
    private Temp temp;
    private JSONController json;

    public TempService() {
        json = new JSONController("temp.txt");
        temp = (Temp) json.read(Temp.class);
    }

    public Temp getTemp() {
        return this.temp;
    }

    public Temp setTemp(Temp temp) {
        this.temp = temp;
        json.write(temp);
        return temp;
    }

    public void clearTemp() {
        json.write(new Temp());
    }

    public void deleteTemp() {
        // 检查任务列表是否为空
        if (temp == null) {
//            wishes = new ArrayList<>();
            System.out.println("temp file is empty");
            return;
        }

        // 遍历任务列表，查找指定的wishId


        Temp temp1=new Temp();

        json.write(temp1); // 更新任务列表到文件中
        System.out.println("logout successfully！");
        JOptionPane.showMessageDialog(null, "logout successfully！", "mention", JOptionPane.INFORMATION_MESSAGE);
        return;

    }

}


}
