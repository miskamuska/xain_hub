package com.somecoder.demo.common.util;

import com.somecoder.demo.common.exception.CustomException;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * @author ：lishan
 * @date ：Created in 2021/1/28 21:34
 * @description：邮件验证码工具类
 */
public class AuthCodeUtils {
    private static final String SYMBOLS = "0123456789";
    /**
     * Math.random生成的是一般随机数，采用的是类似于统计学的随机数生成规则，其输出结果很容易预测，因此可能导致被攻击者击中。
     * 而SecureRandom是真随机数，采用的是类似于密码学的随机数生成规则，其输出结果较难预测，若想要预防被攻击者攻击，最好做到使攻击者根本无法，或不可能鉴别生成的随机值和真正的随机值。
     */
    private static final Random RANDOM = new SecureRandom();

    public static String generateVerCode() {
        char[] nonceChars = new char[6];
        for (int i = 0; i < nonceChars.length; i++) {
            nonceChars[i] = SYMBOLS.charAt(RANDOM.nextInt(nonceChars.length));
        }
        return new String(nonceChars);
    }

    /**
     * 计算两个日期的分钟差
     */
    public static int getMinute(Date fromDate, Date toDate) {
        if (fromDate == null) {
            throw new CustomException(1, "请先发送验证码");
        }
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }
}
