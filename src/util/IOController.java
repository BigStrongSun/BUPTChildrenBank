package util;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IOController {
    private String filename;
    private File file;

    public IOController(String filename) {
        this.filename = filename;
        this.file = new File(getCacheDir() + File.separator + filename);
    }

    private String getCacheDir() {
        try {
            String jarDir = new File(IOController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
            return jarDir + File.separator + "cache";
        } catch (Exception e) {
            throw new RuntimeException("Failed to get cache directory", e);
        }
    }

    public boolean directWrite(String data) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file))) {
            pw.print(data);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String directRead() {
        if (!file.exists()) {
            return null;
        }
        try {
            return new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void copyFileToJar(String sourcePath, String targetPath) throws IOException, URISyntaxException {
        Path source = Paths.get(sourcePath);
        Path jarPath = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        try (FileSystem fs = FileSystems.newFileSystem(jarPath, (ClassLoader) null)) {
            Path target = fs.getPath(targetPath);
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public boolean fileExists() {
        return file.exists();
    }

    public String getFilePath() {
        return file.getPath();
    }
}
