package util;

import java.io.*;

/**
 * The util.IOController class provides some simple methods for file input or output.
 * Title      : util.IOController.java
 * <p>
 * Description:
 * <p>
 * The util.IOController class provides some simple methods for file input or output.
 * <p>
 * The most important feature of this class is that it can switch input and output flexibly
 * and it has complete exception handling.
 * </p>
 * @version 1.2
 */
public class IOController {
    private String filename;
    private File file;
    private boolean isResource;

    /**
     * Constructor of util.IOController.
     * @param filename the name of the file which needs to be I/O.
     */
    public IOController(String filename) {
        this.filename = filename;
        file = new File(filename);
        if (!file.exists()) {
            // Check if the file exists as a resource in JAR
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            if (inputStream != null) {
                isResource = true;
            }
        }
    }

    /**
     * Check if the file exists.
     * @return true if the file exists, false otherwise.
     */
    public boolean fileExists() {
        if (isResource) {
            return getClass().getClassLoader().getResource(filename) != null;
        }
        return file.exists();
    }

    /**
     * Get the absolute path of the file.
     * @return the absolute path of the file.
     */
    public String getFilePath() {
        if (isResource) {
            return getClass().getClassLoader().getResource(filename).getPath();
        }
        return file.getAbsolutePath();
    }

    /**
     * Write data to the file.
     * @param data the data needed to be written.
     * @return Whether it was written successfully or not.
     */
    public boolean directWrite(String data) {
        if (isResource) {
            throw new UnsupportedOperationException("Cannot write to a resource file.");
        }
        try (OutputStream os = new FileOutputStream(file);
             PrintWriter pw = new PrintWriter(os)) {
            pw.print(data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Read data from the file.
     * @return the data which are read from the file.
     */
    public String directRead() {
        if (!fileExists()) {
            return null;
        }
        try (InputStream is = isResource ? getClass().getClassLoader().getResourceAsStream(filename) : new FileInputStream(file);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return new String(buffer.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the file name.
     * @return the filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set the file name.
     * @param filename the filename to set.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Get the file object.
     * @return the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the file object.
     * @param file the file to set.
     */
    public void setFile(File file) {
        this.file = file;
    }
}
