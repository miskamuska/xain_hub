//package com.somecoder.demo.common.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * @author ：lishan
// * @date ：Created in 2021/1/28 16:01
// * @description：先加载过滤器，过滤时@Resource自动注入可能失败，用此工具防止注入为null
// */
//@Slf4j
//@Component
//public class SpringUtils implements ApplicationContextAware {
//    private static ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException {
//        if (SpringUtils.applicationContext == null) {
//            SpringUtils.applicationContext = applicationContext;
//        }
//
//    }
//
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    //根据name
//    public static Object getBean(String name) {
//        return getApplicationContext().getBean(name);
//    }
//
//    //根据类型
//    public static <T> T getBean(Class<T> clazz) {
//        return getApplicationContext().getBean(clazz);
//    }
//
//    public static <T> T getBean(String name, Class<T> clazz) {
//        return getApplicationContext().getBean(name, clazz);
//    }
//}
