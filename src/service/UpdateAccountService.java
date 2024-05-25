package service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UpdateAccountService {

    private static final File jsonFile = new File("account.txt");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static ScheduledExecutorService scheduler;

    // Public method to start scheduled updates
    public static void startScheduledUpdates() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(UpdateAccountService::updateAccounts, 0, 10, TimeUnit.SECONDS);
        }
    }

    private static void updateAccounts() {
        try {
            ArrayNode accounts = (ArrayNode) objectMapper.readTree(jsonFile);
            for (int i = 0; i < accounts.size(); i++) {
                ObjectNode account = (ObjectNode) accounts.get(i);
                if ("SAVING_ACCOUNT".equals(account.get("accountType").asText())) {
                    updateInterest(account);
                    updateLockTimes(account);
                }
            }
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, accounts);
            System.out.println("Accounts updated successfully at " + LocalDateTime.now().format(formatter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



private static void updateInterest(ObjectNode account) {
         double interestRate = 100;  // 利率
        double balance = account.get("balance").asDouble();
        LocalDateTime lastRead = LocalDateTime.parse(account.get("lastReadTime").asText(), formatter);
        long secondsSinceLastRead = ChronoUnit.SECONDS.between(lastRead, LocalDateTime.now());
        double interestMultiplier = (interestRate / 100) / (86400 / 10);  // 每10秒的利息因子

        if (secondsSinceLastRead >= 10) {  // 至少10秒后更新利息
            balance += balance * interestMultiplier * (secondsSinceLastRead / 10);  // 计算新的余额
            account.put("balance", balance);
            account.put("lastReadTime", LocalDateTime.now().format(formatter));  // 更新最后读取时间
        }
    }

    private static void updateLockTimes(ObjectNode account) {
        if (!account.has("depositTime")) {
            LocalDateTime depositTime = LocalDateTime.now();
            account.put("depositTime", depositTime.format(formatter));
            account.put("lockEndTime", depositTime.plusDays(7).format(formatter));  // 解封时间为七天后
        } else {
            // 如果已有depositTime，只更新解封时间
            LocalDateTime depositTime = LocalDateTime.parse(account.get("depositTime").asText(), formatter);
            account.put("lockEndTime", depositTime.plusDays(7).format(formatter));  // 解封时间为七天后
        }
    }
}
