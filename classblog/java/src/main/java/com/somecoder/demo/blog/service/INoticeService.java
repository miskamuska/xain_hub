package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.request.AddNoticeRequest;
import com.somecoder.demo.blog.entity.request.DeleteNoticeRequest;
import com.somecoder.demo.blog.entity.request.ModifyNoticeRequest;
import com.somecoder.demo.blog.entity.request.ShowNoticeListRequest;
import com.somecoder.demo.blog.entity.response.ShowHomePageNoticeResponse;
import com.somecoder.demo.blog.entity.response.ShowNoticeResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
public interface INoticeService extends IService<Notice> {
    /**
     * 显示公告
     *
     * @param showNoticeListRequest 显示公告请求
     * @return 显示帖子响应
     * @author liangkeyu
     * @since 2021-01-28
     */
    Page<ShowNoticeResponse> showNotice(ShowNoticeListRequest showNoticeListRequest);

    /**
     * 添加公告
     *
     * @param userId 用户主键
     * @param addNoticeRequest 添加公告请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void addNotice(String userId, AddNoticeRequest addNoticeRequest);

    /**
     * 删除公告
     *
     * @param deleteNoticeRequest 删除公告请求
     * @author liangkeyu
     * @since 2021-01-28
     */
    void deleteNotice(DeleteNoticeRequest deleteNoticeRequest);

    /**
     * 修改公告
     *
     * @param modifyNoticeRequest 修改公告
     * @author liangkeyu
     * @since 2021-01-28
     */
    void modifyNotice(ModifyNoticeRequest modifyNoticeRequest);

    /**
     * 首页显示公告标题和时间
     * @return 显示公告标题时间响应类
     */
    List<ShowHomePageNoticeResponse> showHomePageNotice();

    /**
     * 显示公告详情
     * @param deleteNoticeRequest 公告主键请求
     * @return 公告详情响应
     */
    ShowNoticeResponse showNoticeDetail(DeleteNoticeRequest deleteNoticeRequest);
}
