package util;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;

/**
 * The util.JSONController class provides some simple methods for JSON input or output.
 * Title      : util.JSONController.java
 * <p>
 * Description:
 * <p>
 * The util.JSONController class provides some simple methods for JSON input or output.
 * <p>
 * The most important feature of this class is that it can allow any class to be
 * saved in the JSON file
 * <p>
 * The class uses the Fastjson library for JSON parsing and serialization.
 * It also uses IOController for file operations.
 * @version 1.2
 */
public class JSONController {
    private IOController io;
    private String filename;

    /**
     * JSONController的构造函数
     * @param filename 需要进行I/O操作的JSON文件的名称
     */
    public JSONController(String filename) {
        this.filename = filename;
        io = new IOController(filename);
        configureJSONParser();
        if (!io.fileExists()) {
            System.err.println("File not found: " + io.getFilePath());
        } else {
            System.out.println("File found: " + io.getFilePath());
        }
    }

    /**
     * 配置Fastjson解析器的各种功能
     */
    private void configureJSONParser() {
        int features = 0;
        features |= Feature.AutoCloseSource.getMask();
        features |= Feature.InternFieldNames.getMask();
        features |= Feature.AllowUnQuotedFieldNames.getMask();
        features |= Feature.AllowSingleQuotes.getMask();
        features |= Feature.AllowArbitraryCommas.getMask();
        features |= Feature.SortFeidFastMatch.getMask();
        features |= Feature.IgnoreNotMatch.getMask();
        JSON.DEFAULT_PARSER_FEATURE = features;
    }

    /**
     * 从JSON文件中读取对象数组
     * @param tClass 文件中对象的类信息
     * @param <T> 任何JavaBean对象
     * @return 包含从文件中读取的对象的数组
     */
    public <T> List<T> readArray(Class<T> tClass) {
        try {
            String json = io.directRead();
            return JSONArray.parseArray(json, tClass);
        } catch (Exception e) {
            System.err.println("Error reading JSON array: " + e.getMessage());
            return null;
        }
    }

    /**
     * 将对象数组写入JSON文件
     * @param objectList 包含将写入文件的对象的列表
     * @return 写入操作是否成功
     */
    public boolean writeArray(List<?> objectList) {
        try {
            String jsonString = JSON.toJSONString(objectList);
            return io.directWrite(jsonString);
        } catch (Exception e) {
            System.err.println("Error writing JSON array: " + e.getMessage());
            return false;
        }
    }

    /**
     * 从JSON文件中读取单个对象
     * @param tClass 对象的类信息
     * @return 从文件中读取的对象
     */
    public <T> T read(Class<T> tClass) {
        try {
            String json = io.directRead();
            return JSON.parseObject(json, tClass);
        } catch (Exception e) {
            System.err.println("Error reading JSON object: " + e.getMessage());
            return null;
        }
    }

    /**
     * 将单个对象写入JSON文件
     * @param x 需要写入文件的对象
     * @return 写入操作是否成功
     */
    public boolean write(Object x) {
        try {
            String jsonString = JSON.toJSONString(x);
            return io.directWrite(jsonString);
        } catch (Exception e) {
            System.err.println("Error writing JSON object: " + e.getMessage());
            return false;
        }
    }
}
