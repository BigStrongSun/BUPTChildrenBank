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
 * </p>
 * @version 1.2
 */
public class JSONController {
    private IOController io;

    /**
     * Constructor of JSONController
     * @param filename the name of the JSON file which needs to be I/O.
     */
    public JSONController(String filename) {
        io = new IOController(filename);
        configureJSONParser();
        if (!io.fileExists()) {
            System.err.println("File not found: " + io.getFilePath());
        } else {
            System.out.println("File found: " + io.getFilePath());
        }
    }

    /**
     * Configures the Fastjson parser with various features.
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
     * Reads an array of objects from the JSON file.
     * @param tClass the class information of the objects in the file
     * @param <T> Any javabean object
     * @return the array containing the objects which are read from the file
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
     * Writes an array of objects to the JSON file.
     * @param objectList the List containing the objects which will be written to the file
     * @return Whether the write operation was successful
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
     * Reads a single object from the JSON file.
     * @param tClass the class information of the object
     * @return the object which is read from the file
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
     * Writes a single object to the JSON file.
     * @param x the object which needs to be written to the file
     * @return Whether the write operation was successful
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
