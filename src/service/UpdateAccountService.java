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

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UpdateAccountService {

    private static final File jsonFile = new File("account.txt");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static ScheduledExecutorService scheduler;

    // Public method to start scheduled updates
    public static void startScheduledUpdates() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(UpdateAccountService::updateAccounts, 0, 120, TimeUnit.SECONDS);
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
        double interestRate = 1.5;  // 假设年利率为1.5%
        BigDecimal balance = new BigDecimal(account.get("balance").asDouble());
        LocalDateTime lastRead = LocalDateTime.parse(account.get("lastReadTime").asText(), formatter);
        long daysSinceLastRead = ChronoUnit.DAYS.between(lastRead, LocalDateTime.now());

        if (daysSinceLastRead >= 1) {  // 至少一天后更新利息
            BigDecimal annualInterestRate = BigDecimal.valueOf(interestRate / 100);  // 年利率转为BigDecimal
            BigDecimal dailyInterestFactor = annualInterestRate.divide(BigDecimal.valueOf(365), 8, RoundingMode.HALF_UP);  // 每天的利息因子，保留8位小数以确保精确度
            BigDecimal interest = balance.multiply(dailyInterestFactor);  // 计算一天的利息
            balance = balance.add(interest).setScale(2, RoundingMode.HALF_UP);  // 将新余额加上利息并保留两位小数

            account.put("balance", balance.doubleValue());  // 将新余额更新回账户对象
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
