package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.AddNoticeRequest;
import com.somecoder.demo.blog.entity.request.DeleteNoticeRequest;
import com.somecoder.demo.blog.entity.request.ModifyNoticeRequest;
import com.somecoder.demo.blog.entity.request.ShowNoticeListRequest;
import com.somecoder.demo.blog.entity.response.ShowHomePageNoticeResponse;
import com.somecoder.demo.blog.entity.response.ShowNoticeResponse;
import com.somecoder.demo.blog.service.INoticeService;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@RequestMapping("/blog/notice")
@Api(tags = "公告相关")
public class NoticeController {

    @Resource
    private INoticeService noticeService;

    @ApiOperation("显示公告")
    @PostMapping("/showNotice")
    public ApiResponse<Page<ShowNoticeResponse>> showNotice(
            @RequestBody ShowNoticeListRequest showNoticeListRequest
    ) {
        Page<ShowNoticeResponse> showNoticeResponse;
        showNoticeResponse = noticeService.showNotice(showNoticeListRequest);
        return ApiResponse.success(showNoticeResponse);
    }

    @ApiOperation("添加公告")
    @PostMapping("/addNotice")
    public ApiResponse<Object> addNotice(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @Validated @RequestBody AddNoticeRequest addNoticeRequest
    ) {
        noticeService.addNotice(loginUser.getUserId(), addNoticeRequest);
        return ApiResponse.success();
    }

    @ApiOperation("删除公告")
    @PostMapping("/deleteNotice")
    public ApiResponse<Object> deleteNotice(
            @Validated @RequestBody DeleteNoticeRequest deleteNoticeRequest
    ) {
        noticeService.deleteNotice(deleteNoticeRequest);
        return ApiResponse.success();
    }

    @ApiOperation("修改公告")
    @PostMapping("/modifyNotice")
    public ApiResponse<Object> modifyNotice(
            @Validated @RequestBody ModifyNoticeRequest modifyNoticeRequest
    ) {
        noticeService.modifyNotice(modifyNoticeRequest);
        return ApiResponse.success();
    }

    @ApiOperation("首页显示公告标题和时间")
    @PostMapping("/showHomePageNotice")
    public ApiResponse<List<ShowHomePageNoticeResponse>> showHomePageNotice() {
        List<ShowHomePageNoticeResponse> list = noticeService.showHomePageNotice();
        return ApiResponse.success(list);
    }

    @ApiOperation("显示公告详情")
    @PostMapping("/showNoticeDeatil")
    public ApiResponse<ShowNoticeResponse> showNoticeDetail(
            @RequestBody DeleteNoticeRequest deleteNoticeRequest
    ) {
        ShowNoticeResponse showNoticeResponse = noticeService.showNoticeDetail(deleteNoticeRequest);
        return ApiResponse.success(showNoticeResponse);
    }

}
