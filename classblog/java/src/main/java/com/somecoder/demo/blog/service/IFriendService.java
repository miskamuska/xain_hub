package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Friend;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.ShowFriendListResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
public interface IFriendService extends IService<Friend> {

    /**
     * 展示会话列表
     * @param loginUser 登录用户
     * @return 好友列表
     */
    List<ShowFriendListResponse> showFriendList(LoginUser loginUser);

    /**
     * 删除会话
     * @param loginUser 当前用户
     * @param showLetterRecordRequest 好友用户主键
     */
    void deleteFriend(LoginUser loginUser, ShowLetterRecordRequest showLetterRecordRequest);

}
