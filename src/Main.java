import pages.LoginPage;
import service.TempService;
import util.IOController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        try {
            // 在启动时，将所有需要的txt文件复制到cache目录
            copyFilesToCache();

            // 添加一个钩子在程序退出时保存文件
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    saveFilesBackToJar();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }));
            TempService tempService = new TempService();
            tempService.clearTemp(); // 清空temp.txt文件内容
            // 启动应用
            new LoginPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将JAR中的文件复制到缓存目录
     */
    private static void copyFilesToCache() {
        String[] files = {"task.txt", "tr.txt", "transaction.txt", "user.txt", "wish.txt", "account.txt", "temp.txt"};
        Path cacheDir;
        try {
            cacheDir = Paths.get(getJarDir(), "cache");
            if (Files.notExists(cacheDir)) {
                Files.createDirectories(cacheDir);
            }
            for (String file : files) {
                Path targetFile = cacheDir.resolve(file);
                try (InputStream is = Main.class.getResourceAsStream("/" + file)) {
                    if (is == null) {
                        throw new FileNotFoundException("Resource not found: " + file);
                    }
                    Files.copy(is, targetFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取JAR文件所在目录
     * @return JAR文件所在目录
     */
    private static String getJarDir() throws URISyntaxException {
        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        return new File(jarPath).getParent();
    }

    /**
     * 将缓存目录中的文件保存回JAR
     */
    private static void saveFilesBackToJar() throws URISyntaxException {
        String[] files = {"task.txt", "tr.txt", "transaction.txt", "user.txt", "wish.txt", "account.txt", "temp.txt"};
        Path cacheDir = Paths.get(getJarDir(), "cache");
        for (String file : files) {
            IOController io = new IOController(file);
            try {
                io.copyFileToJar(cacheDir.resolve(file).toString(), file);
                System.out.println("Successfully copied " + file + " back to JAR.");
            } catch (IOException e) {
                System.err.println("Failed to copy " + file + " back to JAR: " + e.getMessage());
            }
        }
    }
}
