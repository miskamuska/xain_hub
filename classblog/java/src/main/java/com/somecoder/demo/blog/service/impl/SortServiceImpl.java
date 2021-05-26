package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.Post;
import com.somecoder.demo.blog.entity.Sort;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddSortRequest;
import com.somecoder.demo.blog.entity.request.DeleteSortRequest;
import com.somecoder.demo.blog.entity.request.GetSortPostListRequest;
import com.somecoder.demo.blog.entity.request.ModifySortRequest;
import com.somecoder.demo.blog.entity.response.SortPostListResponse;
import com.somecoder.demo.blog.entity.response.SortResponse;
import com.somecoder.demo.blog.mapper.PostMapper;
import com.somecoder.demo.blog.mapper.SortMapper;
import com.somecoder.demo.blog.service.ISortService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements ISortService {

    @Resource
    private PostMapper postMapper;

    @Resource
    private SortMapper sortMapper;

    /**
     *
     * 获取分类帖子列表
     */
    @Override
    public Page<SortPostListResponse> getSortPostList(GetSortPostListRequest getSortPostListRequest) {
        LambdaQueryWrapper<Post> postListQuery = Wrappers.lambdaQuery(Post.class)
                .eq(Post::getSortId, getSortPostListRequest.getSortId());
        // 分页查询
        Page<Post> postListQueryPage = new Page<>();
        BeanUtils.copyProperties(getSortPostListRequest.getPageRequest(), postListQueryPage);
        Page<Post> postPage = postMapper.selectPage(postListQueryPage, postListQuery);
        List<Post> postList = postPage.getRecords();
        Page<SortPostListResponse> postListResponsePage = new Page<>();
        BeanUtils.copyProperties(postPage, postListResponsePage);
        // 分类帖子列表
        List<SortPostListResponse> sortPostListResponseList = new ArrayList<>();
        for (Post post : postList) {
            SortPostListResponse sortPostListResponse = new SortPostListResponse();
            if (post.getSortId().equals(getSortPostListRequest.getSortId())) {
                BeanUtils.copyProperties(post, sortPostListResponse);
            }
            sortPostListResponseList.add(sortPostListResponse);
        }
        postListResponsePage.setRecords(sortPostListResponseList);
        return postListResponsePage;
    }

    /**
     * 获取所有分类
     */
    @Override
    public List<String> getAllSort() {
        List<Sort> sortList = sortMapper.selectList(
                Wrappers.lambdaQuery(Sort.class)
                        .orderByAsc(Sort::getSortTheme)
        );
        List<String> list = new ArrayList<>(sortList.size());
        for (Sort sort : sortList) {
            list.add(sort.getSortTheme());
        }
        return list;
    }

    /**
     * 添加分类
     */
    @Override
    public void addSort(AddSortRequest addSortRequest) {
        int sortCount = sortMapper.selectCount(
                Wrappers.lambdaQuery(Sort.class)
                        .eq(Sort::getSortTheme, addSortRequest.getSortTheme())
        );
        if (sortCount > 0) {
            throw new CustomException("该分类已存在");
        }
        Sort sort = new Sort();
        BeanUtils.copyProperties(addSortRequest, sort);
        sort.setSortId(PrefixConstant.SORT_PREFIX + UUID.randomUUID());
        sortMapper.insert(sort);
    }

    /**
     * 修改分类
     */
    @Override
    public void modifySort(ModifySortRequest modifySortRequest) {
        sortMapper.update(
                null,
                Wrappers.lambdaUpdate(Sort.class)
                        .set(Sort::getSortIntroduction, modifySortRequest.getSortIntroduction())
                        .eq(Sort::getSortId, modifySortRequest.getSortId())
        );
    }

    /**
     * 删除分类
     */
    @Override
    public void deleteSort(DeleteSortRequest deleteSortRequest) {
        sortMapper.delete(
                Wrappers.lambdaQuery(Sort.class)
                        .eq(Sort::getSortId, deleteSortRequest.getSortId())
        );
    }
    /**
     * 写文章界面获取分类列表
     */
    @Override
    public List<SortResponse> writeGetSort() {
        List<Sort> sorts = sortMapper.selectList(null);
        List<SortResponse> list = new ArrayList<>(sorts.size());
        sorts.forEach(sort -> {
            SortResponse sortResponse = new SortResponse();
            sortResponse.setSortTheme(sort.getSortTheme());
            sortResponse.setSortId(sort.getSortId());
            list.add(sortResponse);
        });
        return list;
    }
}
