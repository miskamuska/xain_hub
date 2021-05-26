package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.AddLetterRequest;
import com.somecoder.demo.blog.entity.request.LetterIdRequest;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;
import com.somecoder.demo.blog.entity.response.ShowLetterRecordResponse;
import com.somecoder.demo.blog.service.ILetterService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
@Api(tags = "私信相关")
@AccessToken
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/blog/letter")
public class LetterController {

    @Resource
    private ILetterService letterService;

    @ApiOperation("添加(发送)私信")
    @PostMapping("/addLetter")
    public ApiResponse<Object> addLetter(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody AddLetterRequest addLetterRequest
    ) {
        letterService.addLetter(addLetterRequest, loginUser);
        return ApiResponse.success();
    }

    @ApiOperation("查看私信消息记录")
    @PostMapping("/showLetterRecord")
    public ApiResponse<ShowLetterRecordResponse> showLetterRecord(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody ShowLetterRecordRequest showLetterRecordRequest
    ) {
        ShowLetterRecordResponse showLetterRecordResponse = letterService.showLetterRecord(
                showLetterRecordRequest, loginUser
        );
        return ApiResponse.success(showLetterRecordResponse);
    }

    @ApiOperation("删除一条聊天记录")
    @PostMapping("/deleteLetterRecord")
    public ApiResponse<Object> deleteLetterRecord(
            @RequestBody LetterIdRequest letterIdRequest
    ) {
        letterService.deleteLetterRecord(letterIdRequest);
        return ApiResponse.success();
    }

    @ApiOperation("删除全部记录")
    @PostMapping("deleteAllLetterRecord")
    public ApiResponse<Object> deleteAllLetterRecord(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody ShowLetterRecordRequest showLetterRecordRequest
    ) {
        letterService.deleteAllLetterRecord(loginUser, showLetterRecordRequest);
        return ApiResponse.success();
    }

    @PostMapping("/showAllLetterCount")
    @ApiOperation("显示私信消息总数")
    public ApiResponse<MessageCountResponse> showAllLetterCount(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        MessageCountResponse messageCountResponse = letterService.showAllLetterCount(loginUser);
        return ApiResponse.success(messageCountResponse);
    }

    @PostMapping("/clearAllLetterMessage")
    @ApiOperation("清空所有未读消息")
    public ApiResponse<Object> clearAllLetterMessage(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser
    ) {
        letterService.clearAllLetterMessage(loginUser);
        return ApiResponse.success();
    }

}
