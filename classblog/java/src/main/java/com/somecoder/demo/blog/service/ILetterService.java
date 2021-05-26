package com.somecoder.demo.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.somecoder.demo.blog.entity.Letter;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.request.AddLetterRequest;
import com.somecoder.demo.blog.entity.request.LetterIdRequest;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;
import com.somecoder.demo.blog.entity.response.ShowLetterRecordResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-26
 */
public interface ILetterService extends IService<Letter> {

    /**
     * 添加私信
     * @param addLetterRequest 添加私信请求
     * @param loginUser 当前用户
     */
    void addLetter(AddLetterRequest addLetterRequest, LoginUser loginUser);

    /**
     * 显示消息记录
     * @param showLetterRecordRequest 显示消息记录请求类
     * @param loginUser 当前用户
     * @return 显示记录
     */
    ShowLetterRecordResponse showLetterRecord(ShowLetterRecordRequest showLetterRecordRequest, LoginUser loginUser);

    /**
     * 删除一条消息记录
     * @param letterIdRequest 消息主键
     */
    void deleteLetterRecord(LetterIdRequest letterIdRequest);

    /**
     * 删除所有记录
     * @param loginUser 当前用户
     * @param showLetterRecordRequest 好友用户主键请求
     */
    void deleteAllLetterRecord(LoginUser loginUser, ShowLetterRecordRequest showLetterRecordRequest);

    /**
     * 显示未读消息总数
     * @param loginUser 当前用户
     * @return 未读消息总数
     */
    MessageCountResponse showAllLetterCount(LoginUser loginUser);

    void clearAllLetterMessage(LoginUser loginUser);
}
