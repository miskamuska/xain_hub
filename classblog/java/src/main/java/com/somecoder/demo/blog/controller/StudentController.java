package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.*;
import com.somecoder.demo.blog.entity.response.*;
import com.somecoder.demo.blog.service.IStudentService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@RequestMapping("/blog/student")
@Api(tags = "学生相关")
public class StudentController {

    @Resource
    IStudentService studentService;

    @ApiOperation("展示学生信息")
    @PostMapping("/showInformation")
    public ApiResponse<ShowInformationResponse> showInformation(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        ShowInformationResponse showInformationResponse = studentService.showInformation(loginUser.getUserId());
        return ApiResponse.success(showInformationResponse);
    }

    @ApiOperation("学生列表")
    @PostMapping("/studentList")
    public ApiResponse<Page<StudentListResponse>> studentList(
            @RequestBody StudentListRequest studentListRequest
    ) {
        Page<StudentListResponse> studentListResponsePage = studentService.getStudentList(studentListRequest);
        return ApiResponse.success(studentListResponsePage);
    }

    @ApiOperation("修改学生信息")
    @PostMapping("/changeStudentInformation")
    public ApiResponse<ChangeInformationResponse> changeStudentInformation(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @RequestBody ChangeInformationRequest changeInformationRequest
    ) {
        ChangeInformationResponse changeInformationResponse
                = studentService.changeInformation(loginUser.getUserId(), changeInformationRequest);
        return ApiResponse.success(changeInformationResponse);
    }

    @ApiOperation("显示邮箱")
    @PostMapping("/showEmail")
    public ApiResponse<String> showEmail(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser
    ) {
        String email = studentService.showEmail(loginUser);
        return ApiResponse.success(0, "success", email);
    }

    @ApiOperation("显示密码")
    @PostMapping("/showPwd")
    public ApiResponse<String> showPwd(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser
    ) {
        String replacePwd = UUID.randomUUID().toString().substring(0, loginUser.getPassword().length());
        return ApiResponse.success(0, "success", replacePwd);
    }

    @ApiOperation("修改邮箱")
    @PostMapping("/updateEmail")
    public ApiResponse<Object> updateEmail(
            @Validated @RequestBody UpdateEmailRequest updateEmailRequest
    ) {
        studentService.updateEmail(updateEmailRequest);
        return ApiResponse.success();
    }

    @ApiOperation("验证验证码")
    @PostMapping("/updateEmail/verCode")
    public ApiResponse<Object> verCode(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @Validated @RequestBody UpdateEmailVerCodeRequest updateEmailVerCodeRequest
    ) {
        studentService.verCode(loginUser, updateEmailVerCodeRequest);
        return ApiResponse.success();
    }

    @ApiOperation("修改密码")
    @PostMapping("/changePassword")
    public ApiResponse<Object> changePassword(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER)LoginUser loginUser,
            @Validated @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        studentService.changePassword(loginUser.getUserId(), changePasswordRequest);
        return ApiResponse.success();
    }

    @ApiOperation("显示待审核学生账号")
    @PostMapping("/showReviewStudent")
    public ApiResponse<Page<ShowReviewStudentResponse>> showReviewStudent(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody ShowReviewStudentRequest showReviewStudentRequest
    ) {
        Page<ShowReviewStudentResponse> showReviewStudentResponsePage
                = studentService.showReviewStudent(loginUser.getUserId(), showReviewStudentRequest);
        return ApiResponse.success(showReviewStudentResponsePage);
    }

    @ApiOperation("审核学生账号")
    @PostMapping("/reviewStudentAccounts")
    public ApiResponse<Object> reviewStudentAccounts(
            @RequestBody ReviewStudentAccountsRequest reviewStudentAccountsRequest
    ) {
        studentService.reviewStudentAccounts(reviewStudentAccountsRequest);
        return ApiResponse.success();
    }

    @ApiOperation("删除学生账号")
    @PostMapping("/deleteStudent")
    public ApiResponse<Object> deleteStudent(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody DeleteStudentRequest deleteStudentRequest
    ) {
        studentService.deleteStudent(loginUser.getUserId(), deleteStudentRequest);
        return ApiResponse.success();
    }

    @ApiOperation("显示其他所有学生")
    @PostMapping("/showOtherStudent")
    public ApiResponse<List<ShowOtherStudentResponse>> showOtherStudent(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        List<ShowOtherStudentResponse> list = studentService.showOtherStudent(loginUser);
        return ApiResponse.success(list);
    }
}
