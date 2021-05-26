package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.LoginRequest;
import com.somecoder.demo.blog.entity.request.RegisterRequest;
import com.somecoder.demo.blog.entity.request.ResetPasswordRequest;
import com.somecoder.demo.blog.entity.request.UpdatePwdRequest;
import com.somecoder.demo.blog.entity.response.LoginInformationResponse;

/**
 * <p>
 * 登录表 服务类
 * </p>
 *
 * @author lishan
 * @since 2021-01-28
 */
public interface ILoginUserService extends IService<LoginUser> {
    /**
     * 登录
     *
     * @param loginRequest 登录请求
     * @return LoginInformationResponse 用户信息响应
     * @author lishan
     * @since 2021-01-28
     */
    LoginInformationResponse login(LoginRequest loginRequest);

    /**
     * 注册
     *
     * @param registerRequest 注册请求
     * @author lishan
     * @since 2021-01-28
     */
    void register(RegisterRequest registerRequest);

    /**
     * 忘记密码
     *
     * @param loginId 登录账号
     * @param email 邮箱
     * @author lishan
     * @since 2021-01-28
     */
    void forgetPwd(String loginId, String email);

    /**
     * 修改密码
     *
     * @param updatePwdRequest 修改密码请求
     * @author lishan
     * @since 2021-01-28
     */
    void updatePwd(UpdatePwdRequest updatePwdRequest);

    /**
     * 退出登录
     *
     * @param userId 用户主键
     * @author liangkeyu
     * @since 2021-01-28
     */
    void logout(String userId);

    /**
     * 管理员登录
     * @param loginRequest 管理员登录请求
     * @return LoginInformationResponse 管理员登录信息响应
     * @author lishan
     * @since 2021-01-28
     */
    LoginInformationResponse adminLogin(LoginRequest loginRequest);

    /**
     * 管理员登录
     * @param resetPasswordRequest 重置密码请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
