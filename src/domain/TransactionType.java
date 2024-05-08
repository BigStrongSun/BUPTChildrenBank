package domain;

import java.util.Date; // 导入Date类，用于处理日期

// 交易类型的枚举，定义可能的交易类型
public enum TransactionType {
    TRANSFER, WITHDRAWAL, GIFT_EXCHANGE, BONUS
}