package com.somecoder.demo.blog.service.impl;

import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Student;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.RegisterCodeRequest;
import com.somecoder.demo.blog.entity.request.VerCodeRequest;
import com.somecoder.demo.blog.mapper.LoginUserMapper;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.service.VerCodeService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.exception.CustomException;
import com.somecoder.demo.common.util.AuthCodeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ：lishan
 * @since 2021/1/28
 */
@Service
public class VerCodeServiceImpl implements VerCodeService {


    @Resource
    private LoginUserMapper loginUserMapper;

    @Resource
    private StudentMapper studentMapper;


    @Value("${file.url}")
    private String url;

    /**
     * 验证码
     */
    private String code;
    /**
     * 发送时间
     */
    private Date sendTime;

    @Override
    @Async
    public void getCode(String sender, JavaMailSenderImpl mailSender, String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("班级圈博客");
        sendTime = new Date();
        code = "123456";
//        code = AuthCodeUtils.generateVerCode();
//        message.setText("尊敬的用户,您好:\n"
//                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
//                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文
//        //发件人
//        message.setFrom(sender);
//        message.setCc(sender);
//        //收件人
//        message.setTo(receiver);
//        //发送邮件
//        mailSender.send(message);
        ApiResponse.success();
    }

    /**
     *
     * 判断验证码是否正确并添加信息
     */
    @Override
    public void judgeVerCode(RegisterCodeRequest registerCodeRequest) {

        if (registerCodeRequest.getCode() == null || "".equals(registerCodeRequest.getCode())) {
            throw new CustomException(1, "请输入验证码");
        }

        Date date = new Date();
        //判断验证码
        if (AuthCodeUtils.getMinute(sendTime, date) > CommonConstant.TIME) {
            throw new CustomException(1, "验证码失效");
        }
        if (!registerCodeRequest.getCode().equals(code)) {
            throw new CustomException(1, "验证码错误");
        }
        code = null;


        LoginUser loginUser = new LoginUser();
        loginUser.setPassword(registerCodeRequest.getRegisterRequest().getPassword());
        loginUser.setLoginId(registerCodeRequest.getRegisterRequest().getStudentNumber());
        loginUser.setUserId(loginUser.getLoginId());
        loginUserMapper.insert(loginUser);

        Student student = new Student();
        BeanUtils.copyProperties(registerCodeRequest.getRegisterRequest(), student);
        student.setStudentId(registerCodeRequest.getRegisterRequest().getStudentNumber());
        student.setUserId(registerCodeRequest.getRegisterRequest().getStudentNumber());
        student.setHeadPicUrl(url + "anonymity.jpg");
        student.setBirthday("——-——-——");
        student.setLocation("");
        studentMapper.insert(student);
    }

    /**
     * 判断验证码
     */
    @Override
    public void judgeVerCode(VerCodeRequest verCodeRequest) {

        if (verCodeRequest.getCode() == null || "".equals(verCodeRequest.getCode())) {
            throw new CustomException(1, "请输入验证码");
        }
        Date date = new Date();
        //判断验证码
        if (AuthCodeUtils.getMinute(sendTime, date) > CommonConstant.TIME) {
            throw new CustomException(1, "验证码已经失效");
        }
        if (!verCodeRequest.getCode().equals(code)) {
            throw new CustomException(1, "验证码错误");
        }
        code = null;
//        LoginUser user = loginUserMapper.selectOne(
//                Wrappers.lambdaQuery(LoginUser.class)
//                        .eq(LoginUser::getLoginId, updatePwdRequest.getLoginId())
//        );
//        user.setPassword(updatePwdRequest.getPwd1());
//        loginUserMapper.updateById(user);
    }

    /**
     * 判断验证码
     */
    @Override
    public boolean judgeVerCode(String verCode) {
        if (verCode == null || "".equals(verCode)) {
            throw new CustomException(1, "请输入验证码");
        }
        Date date = new Date();
        //判断验证码
        if (AuthCodeUtils.getMinute(sendTime, date) > CommonConstant.TIME) {
            throw new CustomException(1, "验证码已经失效");
        }
        if (!verCode.equals(code)) {
            throw new CustomException(1, "验证码错误");
        }
        code = null;
        return true;
    }
}
