package com.somecoder.demo.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串处理工具
 * @author zuozhiwei
 */
public class StringUtil {
    /**
     * 异常信息转换为字符串
     * @param stackTraceElements    异常数组
     * @return                      拼接信息
     */
    public static String exceptionPrint(StackTraceElement[] stackTraceElements) {
        List<String> stackList = new ArrayList<>();
        for (StackTraceElement stackTraceElement: stackTraceElements) {
            stackList.add(stackTraceElement.toString());
        }
        return String.join("\n", stackList);
    }
}
