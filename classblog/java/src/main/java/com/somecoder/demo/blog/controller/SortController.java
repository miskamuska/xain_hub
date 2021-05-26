package com.somecoder.demo.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.request.AddSortRequest;
import com.somecoder.demo.blog.entity.request.DeleteSortRequest;
import com.somecoder.demo.blog.entity.request.GetSortPostListRequest;
import com.somecoder.demo.blog.entity.request.ModifySortRequest;
import com.somecoder.demo.blog.entity.response.SortPostListResponse;
import com.somecoder.demo.blog.entity.response.SortResponse;
import com.somecoder.demo.blog.service.ISortService;
import com.somecoder.demo.common.ApiResponse;
import com.somecoder.demo.common.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Slf4j
@AccessToken
@CrossOrigin
@RestController
@RequestMapping("/blog/sort")
@Api(tags = "分类相关")
public class SortController {

    @Resource
    private ISortService sortService;

    @ApiOperation("获取所有分类")
    @PostMapping("/getAllSort")
    public ApiResponse<List<String>> getAllSort() {
        List<String> list = sortService.getAllSort();
        return ApiResponse.success(list);
    }

    @ApiOperation("写文章获取所有分类")
    @PostMapping("/writeGetSort")
    public ApiResponse<List<SortResponse>> writeGetSort() {
        List<SortResponse> list = sortService.writeGetSort();
        return ApiResponse.success(list);
    }


    @ApiOperation("获取分类的帖子列表")
    @PostMapping("/getSortPostList")
    public ApiResponse<Page<SortPostListResponse>> getSortPostList(
            @RequestBody GetSortPostListRequest getSortPostListRequest
    ) {
        Page<SortPostListResponse> sortPostListResponsePage = sortService.getSortPostList(
                getSortPostListRequest
        );
        return ApiResponse.success(sortPostListResponsePage);
    }

    @ApiOperation("添加分类")
    @PostMapping("/addSort")
    public ApiResponse<Object> addSort(
            @Validated @RequestBody AddSortRequest addSortRequest
    ) {
        sortService.addSort(addSortRequest);
        return ApiResponse.success();
    }

    @ApiOperation("修改分类")
    @PostMapping("/modifySort")
    public ApiResponse<Object> modifySort(
            @Validated @RequestBody ModifySortRequest modifySortRequest
    ) {
        sortService.modifySort(modifySortRequest);
        return ApiResponse.success();
    }

    @ApiOperation("删除分类")
    @PostMapping("/deleteSort")
    public ApiResponse<Object> deleteSort(
            @Validated @RequestBody DeleteSortRequest deleteSortRequest
    ) {
        sortService.deleteSort(deleteSortRequest);
        return ApiResponse.success();
    }
}
