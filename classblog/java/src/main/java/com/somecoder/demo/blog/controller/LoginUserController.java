package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.LoginInformationResponse;
import com.somecoder.demo.blog.service.ILoginUserService;
import com.somecoder.demo.blog.service.VerCodeService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 * 登录表 前端控制器
 * </p>
 *
 * @author lishan
 * @since 2021-01-28
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/blog")
@Api(tags = "登录注册相关")
public class LoginUserController {

    @Resource
    private ILoginUserService loginUserService;

    @Resource
    private VerCodeService verCodeService;


    @PostMapping("/login")
    @ApiOperation("登录")
    public ApiResponse<LoginInformationResponse> login(
            @Validated @RequestBody LoginRequest loginRequest
    ) {
        LoginInformationResponse loginInformationResponse = loginUserService.login(loginRequest);
        return ApiResponse.success(loginInformationResponse);
    }

    @PostMapping("/admin-login")
    @ApiOperation(value = "管理员登录")
    public ApiResponse<LoginInformationResponse> adminLogin(
            @Validated @RequestBody LoginRequest loginRequest
    ) {
        LoginInformationResponse loginInformationResponse = loginUserService.adminLogin(loginRequest);
        return ApiResponse.success(loginInformationResponse);
    }

    @PostMapping("/register")
    @ApiOperation("注册，发送验证码")
    public ApiResponse<Object> register(
            @Validated @RequestBody RegisterRequest registerRequest
    ) {
        loginUserService.register(registerRequest);
        return ApiResponse.success();
    }

    @PostMapping("/register/code")
    @ApiOperation("注册，验证验证码")
    public ApiResponse<Object> judgeVerCode(
            @Validated @RequestBody RegisterCodeRequest registerCodeRequest
    ) {
        verCodeService.judgeVerCode(registerCodeRequest);
        return ApiResponse.success();
    }

    @PostMapping("/login/forget-pwd")
    @ApiOperation("忘记密码发送验证码")
    public ApiResponse<Object> forgetPwd(
            @Validated @RequestBody ForgetPwdRequest forgetPwdRequest) {
        loginUserService.forgetPwd(forgetPwdRequest.getLoginId(), forgetPwdRequest.getEmail());
        return ApiResponse.success();
    }

    @PostMapping("/login/forget-pwd/code")
    @ApiOperation("忘记密码验证验证码")
    public ApiResponse<Object> verCode(
            @Validated @RequestBody VerCodeRequest verCodeRequest
    ) {
        verCodeService.judgeVerCode(verCodeRequest);
        return ApiResponse.success();
    }

    @PostMapping("/login/forget-pwd/update")
    @ApiOperation("修改密码")
    public ApiResponse<Object> update(
            @Validated @RequestBody UpdatePwdRequest updatePwdRequest
    ) {
        loginUserService.updatePwd(updatePwdRequest);
        return ApiResponse.success();
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public ApiResponse<Object> resetPassword(
            @Validated @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        loginUserService.resetPassword(resetPasswordRequest);
        return ApiResponse.success();
    }

    @AccessToken
    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public ApiResponse<Object> logout(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser
    ) {
        loginUserService.logout(loginUser.getUserId());
        return ApiResponse.success();
    }
}
