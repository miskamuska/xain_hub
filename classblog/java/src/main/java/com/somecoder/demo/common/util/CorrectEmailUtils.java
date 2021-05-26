package com.somecoder.demo.common.util;

import java.util.regex.Pattern;

/**
 * @author ：lishan
 * @date ：Created in 2021/1/31 9:24
 * @description：判断邮箱格式是否正确
 */
public class CorrectEmailUtils {
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }
}
