package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Student;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.LoginRequest;
import com.somecoder.demo.blog.entity.request.RegisterRequest;
import com.somecoder.demo.blog.entity.request.ResetPasswordRequest;
import com.somecoder.demo.blog.entity.request.UpdatePwdRequest;
import com.somecoder.demo.blog.entity.response.LoginInformationResponse;
import com.somecoder.demo.blog.mapper.LoginUserMapper;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.service.ILoginUserService;
import com.somecoder.demo.blog.service.VerCodeService;
import com.somecoder.demo.common.exception.CustomException;
import com.somecoder.demo.common.util.CorrectEmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author lishan
 * @since 2021-01-28
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements ILoginUserService {

    @Resource
    private LoginUserMapper loginUserMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private VerCodeService verCodeService;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     *
     * 学生登录
     */
    @Override
    public LoginInformationResponse login(LoginRequest loginRequest) {
        LoginUser loginUser = loginUserMapper.selectOne(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getLoginId, loginRequest.getLoginId())
        );
        if (loginUser == null) {
            throw new CustomException(1, "不存在此用户");
        }
        if (!loginUser.getPassword().equals(loginRequest.getPassword())) {
            throw new CustomException(1, "密码错误");
        }
        if (loginUser.getUserType() == -1) {
            throw new CustomException(1, "班长还未通过审核，请联系班长审核");
        }
        // 设置token
        loginUser.setToken(PrefixConstant.TOKEN_PREFIX + UUID.randomUUID());
        // 更新token
        loginUserMapper.updateById(loginUser);
        LoginInformationResponse loginInformationResponse = new LoginInformationResponse();
        loginInformationResponse.setToken(loginUser.getToken());
        return loginInformationResponse;
    }

    /**
     *
     * 管理员登录
     */
    @Override
    public LoginInformationResponse adminLogin(LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        LoginUser loginUser = loginUserMapper.selectOne(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getLoginId, loginId)
        );
        if (loginUser == null) {
            throw new CustomException("不存在此用户");
        }
        if (!loginUser.getPassword().equals(loginRequest.getPassword())) {
            throw new CustomException("密码错误");
        }
        // 判断用户权限
        if (!loginUser.getUserType().equals(CommonConstant.ADMIN)
                && !loginUser.getUserType().equals(CommonConstant.MONITOR)) {
            throw new CustomException("您暂无此权限");
        }
        loginUser.setToken(PrefixConstant.TOKEN_PREFIX + UUID.randomUUID());
        loginUserMapper.updateById(loginUser);
        LoginInformationResponse loginInformationResponse = new LoginInformationResponse();
        BeanUtils.copyProperties(loginUser, loginInformationResponse);
        return loginInformationResponse;
    }

    /**
     *
     * 重置密码
     */
    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        loginUserMapper.update(
                null,
                Wrappers.lambdaUpdate(LoginUser.class)
                        .set(LoginUser::getPassword, "111111")
                        .eq(LoginUser::getLoginId, resetPasswordRequest.getLoginId())
        );
    }

    /**
     *
     * 注册
     */
    @Override
    public void register(RegisterRequest registerRequest) {
        int count = studentMapper.selectCount(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getStudentNumber, registerRequest.getStudentNumber())
        );
        if (count > 0) {
            throw new CustomException(1, "该账号已存在");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getPassword1())) {
            throw new CustomException(1, "两次输入的密码不一致");
        }
        if (registerRequest.getPassword().length() < CommonConstant.MAXLENGTH) {
            throw new CustomException(1, "密码长度不得少于6位");
        }
        if (!CorrectEmailUtils.isValidEmail(registerRequest.getEmail())) {
            throw new CustomException(1, "邮箱格式错误");
        }
        if (registerRequest.getStudentNumber().length() != CommonConstant.MAXLENGTH) {
            throw new CustomException(1, "学号长度应为六位");
        }
        // 发送验证码
        verCodeService.getCode(sender, mailSender, registerRequest.getEmail());
    }

    /**
     *
     * 忘记密码
     */
    @Override
    public void forgetPwd(String loginId, String email) {
        Student student = studentMapper.selectOne(
                Wrappers
                        .lambdaQuery(Student.class)
                        .eq(Student::getStudentNumber, loginId)
                        .eq(Student::getEmail, email)
        );
        if (student == null) {
            throw new CustomException("学号与邮箱不匹配");
        }
        // 通过邮箱验证
        verCodeService.getCode(sender, mailSender, email);
    }

    /**
     *
     * 设置新密码
     */
    @Override
    public void updatePwd(UpdatePwdRequest updatePwdRequest) {
        if (!updatePwdRequest.getPwd1().equals(updatePwdRequest.getPwd2())) {
            throw new CustomException(1, "两次密码不一致");
        }
        if (updatePwdRequest.getPwd1().length() < CommonConstant.MAXLENGTH) {
            throw new CustomException(1, "密码长度不能少于6位");
        }
        LoginUser user = loginUserMapper.selectOne(
                Wrappers.lambdaQuery(LoginUser.class)
                        .eq(LoginUser::getLoginId, updatePwdRequest.getLoginId())
        );
        user.setPassword(updatePwdRequest.getPwd1());
        loginUserMapper.updateById(user);
    }

    /**
     *
     * 退出登录
     */
    @Override
    public void logout(String userId) {
        // 将token赋空字符串
        loginUserMapper.update(
                null,
                Wrappers.lambdaUpdate(LoginUser.class)
                        .set(LoginUser::getToken, "")
                        .eq(LoginUser::getUserId, userId)
        );
    }
}
