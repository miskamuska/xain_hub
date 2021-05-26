package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.response.ShowHeadPicResponse;
import com.somecoder.demo.blog.service.IStudentService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@CrossOrigin
@Slf4j
@AccessToken
@Api(tags = "头像相关")
@RestController
@RequestMapping("/blog/head-pic")
public class HeadPicController {

    @Resource
    private IStudentService studentService;

    @ApiOperation("上传头像")
    @PostMapping("/uploadHeadPic")
    public ApiResponse<String> headPic(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestPart MultipartFile file
    ) {
        String name;
        try {
            name = studentService.uploadHeadPic(file, loginUser);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage());
        }
        return ApiResponse.success(0, "success", name);

    }

    @ApiOperation("显示头像")
    @PostMapping("/showHeadPic")
    public ApiResponse<ShowHeadPicResponse> showHeadPic(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        ShowHeadPicResponse showHeadPicResponse = studentService.showHeadPic(loginUser);
        return ApiResponse.success(0, "success", showHeadPicResponse);
    }

}
