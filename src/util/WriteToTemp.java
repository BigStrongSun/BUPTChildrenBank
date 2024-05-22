package util;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToTemp {
    //向temp.txt中写信息
    public static void writeToTempFile(int childId, String name, boolean isParent, int parentId) {
        // 定义要写入文件的内容
        String content = "{\"childId\":" + childId + "," +
                "\"name\":" + "\"" + name + "\"" + "," +
                "\"isParent\":" + isParent + "," +
                "\"parentId\":" + parentId + "}";

        // 定义文件名
        String fileName = "temp.txt";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            // 将内容写入文件
            fileWriter.write(content);
            System.out.println("信息已成功写入 " + fileName);
        } catch (IOException e) {
            // 处理可能发生的 IO 异常
            e.printStackTrace();
        }
    }
}

