package com.somecoder.demo.blog.controller;


import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.constant.CommonConstant;
import com.somecoder.demo.blog.entity.request.AddCollectRequest;
import com.somecoder.demo.blog.entity.request.PostListRequest;
import com.somecoder.demo.blog.entity.response.GetMyCollectListResponse;
import com.somecoder.demo.blog.service.ICollectService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@CrossOrigin
@AccessToken
@Api(tags = "收藏相关")
@RestController
@RequestMapping("/blog/collect")
public class CollectController {

    @Resource
    private ICollectService collectService;

    @ApiOperation("添加收藏")
    @PostMapping("/addCollect")
    public ApiResponse<Object> addCollect(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody AddCollectRequest addCollectRequest
    ) {
        collectService.addCollect(addCollectRequest, loginUser);
        return ApiResponse.success("收藏成功");
    }

    @ApiOperation("取消收藏")
    @PostMapping("deleteCollect")
    public ApiResponse<Object> deleteCollect(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody AddCollectRequest addCollectRequest
    ) {
        collectService.deleteCollect(addCollectRequest, loginUser);
        return ApiResponse.success();
    }

    @ApiOperation("显示收藏数")
    @PostMapping("/showCountCollect")
    public ApiResponse<Integer> showCountCollect(
            @RequestBody AddCollectRequest addCollectRequest
    ) {
        int count = collectService.showCountCollect(addCollectRequest);
        return ApiResponse.success(count);
    }

    @ApiOperation("个人主页显示我的收藏列表")
    @PostMapping("/showMyCollect")
    public ApiResponse<List<GetMyCollectListResponse>> showMyCollect(
            @ApiIgnore @RequestAttribute(name = CommonConstant.LOGIN_USER) LoginUser loginUser,
            @RequestBody PostListRequest postListRequest
    ) {
        List<GetMyCollectListResponse> list = collectService.showMyCollect(postListRequest, loginUser);
        return ApiResponse.success(list);
    }
}
