package com.somecoder.demo.blog.service;

import com.somecoder.demo.blog.entity.request.RegisterCodeRequest;
import com.somecoder.demo.blog.entity.request.VerCodeRequest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author lishan
 * @since 2021-01-28
 */
public interface VerCodeService {
    /**
     * 发送验证码到指定邮箱
     * @param sender 发送地址
     * @param mailSender spring自带
     * @param receiver 接受地址
     * @author lishan
     * @since 2021-01-28
     */
    void getCode(String sender, JavaMailSenderImpl mailSender, String receiver);

    /**
     * 判断验证码
     * @param registerCodeRequest 注册请求
     * @author lishan
     * @since 2021-01-28
     */
    void judgeVerCode(RegisterCodeRequest registerCodeRequest);

    /**
     * 判断验证码
     * @param verCodeRequest 验证码
     * @author lishan
     * @since 2021-01-28
     */
    void judgeVerCode(VerCodeRequest verCodeRequest);


    /**
     * 验证验证码
     * @param verCode 验证码
     * @return 是否正确
     */
    boolean judgeVerCode(String verCode);


}
