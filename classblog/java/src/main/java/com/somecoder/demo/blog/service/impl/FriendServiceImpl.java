package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.Friend;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.ShowFriendListResponse;
import com.somecoder.demo.blog.mapper.FriendMapper;
import com.somecoder.demo.blog.service.IFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    @Resource
    private FriendMapper friendMapper;


    /**
     * 显示好友列表
     */
    @Override
    public List<ShowFriendListResponse> showFriendList(LoginUser loginUser) {
        List<ShowFriendListResponse> showFriendList = friendMapper.showFriendList(loginUser.getUserId());
        List<ShowFriendListResponse> list = new LinkedList<>(showFriendList);

        List<ShowFriendListResponse> resultList = new ArrayList<>();
        Set<ShowFriendListResponse> set = new HashSet<>();
        showFriendList.forEach(temp -> {
            if (set.add(temp)) {
                resultList.add(temp);
            }
        });

        resultList.forEach(temp -> {
            int sum = 0;
            for (Iterator<ShowFriendListResponse> iterator = list.iterator(); iterator.hasNext(); ) {
                ShowFriendListResponse next = iterator.next();
                if (!next.getIsRead()) {
                    if (temp.getFriendUserId().equals(next.getFriendUserId())
                            && loginUser.getUserId().equals(next.getToUserId())) {
                        sum++;
                        iterator.remove();
                    }
                }
            }
            temp.setCount(sum);
        });

        return resultList;
    }

    /**
     * 删除好友
     */
    @Override
    public void deleteFriend(LoginUser loginUser, ShowLetterRecordRequest showLetterRecordRequest) {
        friendMapper.delete(
                Wrappers.lambdaQuery(Friend.class)
                        .eq(Friend::getFriendUserId, showLetterRecordRequest.getFriendUserId())
                        .eq(Friend::getUserId, loginUser.getUserId())
        );
    }

}
