package util;

import domain.Temp;

public class WriteToTemp {
    // 向 temp.txt 中写信息
    public static void writeToTempFile(int parentId, int childId, boolean isParent, String name) {
        // 创建 Temp 对象
        Temp temp = new Temp(parentId, childId, isParent, name);

        // 定义 JSONController
        JSONController jsonController = new JSONController("temp.txt");

        try {
            // 将 Temp 对象写入 temp.txt
            jsonController.write(temp);

            // 输出成功写入的信息
            System.out.println("信息已成功写入 temp.txt: " + temp.toStringABC());
        } catch (Exception e) {
            // 处理可能发生的异常
            System.err.println("Error writing to temp.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
