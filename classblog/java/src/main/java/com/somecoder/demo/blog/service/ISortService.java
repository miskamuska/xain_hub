package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Sort;
import com.somecoder.demo.blog.entity.request.AddSortRequest;
import com.somecoder.demo.blog.entity.request.DeleteSortRequest;
import com.somecoder.demo.blog.entity.request.GetSortPostListRequest;
import com.somecoder.demo.blog.entity.request.ModifySortRequest;
import com.somecoder.demo.blog.entity.response.SortPostListResponse;
import com.somecoder.demo.blog.entity.response.SortResponse;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface ISortService extends IService<Sort> {
    /**
     * 获取分类帖子列表
     *
     * @param getSortPostListRequest 获取分类帖子列表请求
     * @return 分类帖子列表响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<SortPostListResponse> getSortPostList(GetSortPostListRequest getSortPostListRequest);

    /**
     * 获取所有可选的分类
     * @return 分类列表
     * @author lishan
     * @since 2021-01-28
     */
    List<String> getAllSort();

    /**
     * 添加分类
     *
     * @param addSortRequest 添加分类请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void addSort(AddSortRequest addSortRequest);

    /**
     * 修改分类
     *
     * @param modifySortRequest 修改分类请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void modifySort(ModifySortRequest modifySortRequest);

    /**
     * 删除分类
     *
     * @param deleteSortRequest 删除分类请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deleteSort(DeleteSortRequest deleteSortRequest);

    /**
     * 写文章获取分类
     * @return 分类列表
     */
    List<SortResponse> writeGetSort();
}
