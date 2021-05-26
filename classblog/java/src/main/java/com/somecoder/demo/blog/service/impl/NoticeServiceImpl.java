package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.somecoder.demo.blog.entity.Notice;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddNoticeRequest;
import com.somecoder.demo.blog.entity.request.DeleteNoticeRequest;
import com.somecoder.demo.blog.entity.request.ModifyNoticeRequest;
import com.somecoder.demo.blog.entity.request.ShowNoticeListRequest;
import com.somecoder.demo.blog.entity.response.ShowHomePageNoticeResponse;
import com.somecoder.demo.blog.entity.response.ShowNoticeResponse;
import com.somecoder.demo.blog.mapper.NoticeMapper;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-01-28
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Resource
    NoticeMapper noticeMapper;

    @Resource
    StudentMapper studentMapper;

    /**
     *
     * 显示公告
     */
    @Override
    public Page<ShowNoticeResponse> showNotice(ShowNoticeListRequest showNoticeListRequest) {
        // 模糊查询公告的内容
        LambdaQueryWrapper<Notice> noticeListQuery = Wrappers.lambdaQuery(Notice.class)
                                                             .orderByDesc(Notice::getCreateTime);
        if (StringUtils.isNotBlank(showNoticeListRequest.getContent())) {
            noticeListQuery.like(Notice::getNoticeContent, showNoticeListRequest.getContent());
        }
        if (StringUtils.isNotBlank(showNoticeListRequest.getTheme())) {
            noticeListQuery.like(Notice::getNoticeTheme, showNoticeListRequest.getTheme());
        }
        // 分页查询
        Page<Notice> noticeListQueryPage = new Page<>();
        BeanUtils.copyProperties(showNoticeListRequest.getPostListRequest(), noticeListQueryPage);
        Page<Notice> noticePage = noticeMapper.selectPage(noticeListQueryPage, noticeListQuery);
        List<Notice> noticeList = noticePage.getRecords();
        Page<ShowNoticeResponse> showNoticeResponsePage = new Page<>();
        BeanUtils.copyProperties(noticePage, showNoticeResponsePage);
        // 公告列表
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ShowNoticeResponse> showNoticeResponseList = new ArrayList<>();
        for (Notice notice : noticeList) {
            ShowNoticeResponse showNoticeResponse = new ShowNoticeResponse();
            BeanUtils.copyProperties(notice, showNoticeResponse);
            showNoticeResponse.setCreateTime(dtf.format(notice.getCreateTime()));
            showNoticeResponse.setName(studentMapper.getStudentName(notice.getUserId()));
            showNoticeResponseList.add(showNoticeResponse);
            showNoticeResponse.setNoticeContent(HtmlUtils.htmlUnescape(notice.getNoticeContent()));
        }
        showNoticeResponsePage.setRecords(showNoticeResponseList);
        return showNoticeResponsePage;
    }

    /**
     *
     * 添加公告
     */
    @Override
    public void addNotice(String userId, AddNoticeRequest addNoticeRequest) {
        addNoticeRequest.setNoticeContent(HtmlUtils.htmlEscape(addNoticeRequest.getNoticeContent()));
        Notice notice = new Notice();
        BeanUtils.copyProperties(addNoticeRequest, notice);
        notice.setUserId(userId);
        notice.setNoticeId(PrefixConstant.NOTICE_PREFIX + UUID.randomUUID());
        noticeMapper.insert(notice);
    }

    /**
     *
     * 删除公告
     */
    @Override
    public void deleteNotice(DeleteNoticeRequest deleteNoticeRequest) {
        Notice notice = noticeMapper.selectOne(
                Wrappers.lambdaQuery(Notice.class)
                        .eq(Notice::getNoticeId, deleteNoticeRequest.getNoticeId())
        );
        if (notice == null) {
            throw new CustomException("该公告不存在");
        }
        noticeMapper.delete(
                Wrappers.lambdaQuery(Notice.class)
                        .eq(Notice::getNoticeId, deleteNoticeRequest.getNoticeId())
        );
    }

    /**
     *
     * 修改公告
     */
    @Override
    public void modifyNotice(ModifyNoticeRequest modifyNoticeRequest) {
        modifyNoticeRequest.setNoticeContent(HtmlUtils.htmlEscape(modifyNoticeRequest.getNoticeContent()));
        Notice notice = noticeMapper.selectOne(
                Wrappers.lambdaQuery(Notice.class)
                        .eq(Notice::getNoticeId, modifyNoticeRequest.getNoticeId())
        );
        if (notice == null) {
            throw new CustomException("该公告不存在");
        }
        // 修改公告的内容
        if (StringUtils.isNotBlank(modifyNoticeRequest.getNoticeContent())){
            notice.setNoticeContent(modifyNoticeRequest.getNoticeContent());
        }
        // 修改公告的标题
        if (StringUtils.isNotBlank(modifyNoticeRequest.getNoticeTheme())){
            notice.setNoticeTheme(modifyNoticeRequest.getNoticeTheme());
        }
        noticeMapper.updateById(notice);
    }

    /**
     * 显示主页公告
     */
    @Override
    public List<ShowHomePageNoticeResponse> showHomePageNotice() {
        List<Notice> notices = noticeMapper.selectList(
                Wrappers.lambdaQuery(Notice.class).orderByDesc(Notice::getCreateTime)
        );
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM dd");
        List<ShowHomePageNoticeResponse> list = new ArrayList<>(notices.size());
        for (Notice notice : notices) {
            ShowHomePageNoticeResponse showHomePageNoticeResponse = new ShowHomePageNoticeResponse();
            showHomePageNoticeResponse.setNoticeTheme(notice.getNoticeTheme());
            showHomePageNoticeResponse.setNoticeId(notice.getNoticeId());
            String format = dateTimeFormatter.format(notice.getCreateTime());
            String[] strings = format.split(" ");
            showHomePageNoticeResponse.setYearMonth(strings[0]);
            showHomePageNoticeResponse.setDay(strings[1]);
            list.add(showHomePageNoticeResponse);
        }
        return list;
    }

    /**
     * 显示文章详情
     */
    @Override
    public ShowNoticeResponse showNoticeDetail(DeleteNoticeRequest deleteNoticeRequest) {
        Notice notice = noticeMapper.selectOne(
                Wrappers.lambdaQuery(Notice.class)
                        .eq(Notice::getNoticeId, deleteNoticeRequest.getNoticeId())
        );
        if (notice == null) {
            throw new CustomException(1, "此公告不存在或已被删除");
        }
        String studentName = studentMapper.getStudentName(notice.getUserId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(notice.getCreateTime());
        ShowNoticeResponse showNoticeResponse = new ShowNoticeResponse();
        BeanUtils.copyProperties(notice, showNoticeResponse);
        showNoticeResponse.setCreateTime(format);
        showNoticeResponse.setName(studentName);
        showNoticeResponse.setNoticeContent(HtmlUtils.htmlUnescape(notice.getNoticeContent()));
        return showNoticeResponse;
    }
}
