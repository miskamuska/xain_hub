package com.somecoder.demo.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.somecoder.demo.blog.entity.Friend;
import com.somecoder.demo.blog.entity.Letter;
import com.somecoder.demo.blog.entity.LoginUser;
import com.somecoder.demo.blog.entity.Student;
import com.somecoder.demo.blog.entity.constant.PrefixConstant;
import com.somecoder.demo.blog.entity.request.AddLetterRequest;
import com.somecoder.demo.blog.entity.request.LetterIdRequest;
import com.somecoder.demo.blog.entity.request.ShowLetterRecordRequest;
import com.somecoder.demo.blog.entity.response.MessageCountResponse;
import com.somecoder.demo.blog.entity.response.RecordResponse;
import com.somecoder.demo.blog.entity.response.ShowLetterRecordResponse;
import com.somecoder.demo.blog.mapper.FriendMapper;
import com.somecoder.demo.blog.mapper.LetterMapper;
import com.somecoder.demo.blog.mapper.StudentMapper;
import com.somecoder.demo.blog.service.ILetterService;
import com.somecoder.demo.common.exception.CustomException;
import org.springframework.stereotype.Service;

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
 * @since 2021-02-26
 */
@Service
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements ILetterService {

    @Resource
    private LetterMapper letterMapper;

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private StudentMapper studentMapper;

    /**
     * 添加私信
     */
    @Override
    public void addLetter(AddLetterRequest addLetterRequest, LoginUser loginUser) {
        if (addLetterRequest.getToUserId() == null || "".equals(addLetterRequest.getToUserId())) {
            throw new CustomException(1, "请选择接收人");
        }
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, addLetterRequest.getToUserId())
        );
        if (student == null) {
            throw new CustomException(1, "该用户不存在");
        }
        Friend friend = friendMapper.selectOne(
                Wrappers.lambdaQuery(Friend.class)
                        .eq(Friend::getFriendUserId, addLetterRequest.getToUserId())
                        .eq(Friend::getUserId, loginUser.getUserId())
        );
        if (friend == null) {
            friend = new Friend();
            friend.setFriendUserId(addLetterRequest.getToUserId());
            friend.setUserId(loginUser.getUserId());
            friendMapper.insert(friend);
        }
        friend = friendMapper.selectOne(
                Wrappers.lambdaQuery(Friend.class)
                        .eq(Friend::getUserId, addLetterRequest.getToUserId())
                        .eq(Friend::getFriendUserId, loginUser.getUserId())
        );
        if (friend == null) {
            friend = new Friend();
            friend.setUserId(addLetterRequest.getToUserId());
            friend.setFriendUserId(loginUser.getUserId());
            friendMapper.insert(friend);
        }

        Letter letter = new Letter();
        letter.setLetterId(PrefixConstant.LETTER_PREFIX + UUID.randomUUID());
        letter.setContent(addLetterRequest.getContent());
        letter.setFromUserId(loginUser.getUserId());
        letter.setToUserId(addLetterRequest.getToUserId());
        letterMapper.insert(letter);
    }

    /**
     * 显示私信记录
     */
    @Override
    public ShowLetterRecordResponse showLetterRecord(ShowLetterRecordRequest showLetterRecordRequest, LoginUser loginUser) {
        LambdaUpdateWrapper<Letter> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Letter::getToUserId, loginUser.getUserId())
                .eq(Letter::getFromUserId, showLetterRecordRequest.getFriendUserId());
        updateWrapper.set(Letter::getIsRead, true);
        letterMapper.update(null, updateWrapper);

        LambdaQueryWrapper<Letter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Letter::getFromUserId, loginUser.getUserId())
                .eq(Letter::getToUserId, showLetterRecordRequest.getFriendUserId());
        wrapper.or(temp -> temp.eq(Letter::getFromUserId, showLetterRecordRequest.getFriendUserId())
                .eq(Letter::getToUserId, loginUser.getUserId()));
        List<Letter> letters = letterMapper.selectList(wrapper);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<RecordResponse> recordList = new ArrayList<>(letters.size());
        letters.forEach(letter -> {
            RecordResponse record = new RecordResponse();
            record.setContent(letter.getContent());
            record.setIsMe(letter.getFromUserId().equals(loginUser.getUserId()));
            record.setTime(dateTimeFormatter.format(letter.getCreateTime()));
            record.setLetterId(letter.getLetterId());
            recordList.add(record);
        });
        ShowLetterRecordResponse showLetterRecordResponse = new ShowLetterRecordResponse();
        showLetterRecordResponse.setRecord(recordList);
        Student student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, showLetterRecordRequest.getFriendUserId())
        );
        if (student != null) {
            showLetterRecordResponse.setFriendUserId(student.getUserId());
            showLetterRecordResponse.setHeadPicUrl(student.getHeadPicUrl());
            showLetterRecordResponse.setFriendName(student.getName());
        } else {
            throw new CustomException(1, "该用户不存在");
        }
        student = studentMapper.selectOne(
                Wrappers.lambdaQuery(Student.class)
                        .eq(Student::getUserId, loginUser.getUserId())
        );
        showLetterRecordResponse.setMyUserId(loginUser.getUserId());
        showLetterRecordResponse.setMyHeadPicUrl(student.getHeadPicUrl());
        return showLetterRecordResponse;
    }

    /**
     * 删除私信记录
     */
    @Override
    public void deleteLetterRecord(LetterIdRequest letterIdRequest) {
        letterMapper.delete(
                Wrappers.lambdaQuery(Letter.class)
                        .eq(Letter::getLetterId, letterIdRequest.getLetterId())
        );
    }

    /**
     * 删除所有私信记录
     */
    @Override
    public void deleteAllLetterRecord(LoginUser loginUser, ShowLetterRecordRequest showLetterRecordRequest) {
        LambdaQueryWrapper<Letter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Letter::getFromUserId, loginUser.getUserId())
                .eq(Letter::getToUserId, showLetterRecordRequest.getFriendUserId());
        wrapper.or(temp -> temp.eq(Letter::getFromUserId, showLetterRecordRequest.getFriendUserId())
                .eq(Letter::getToUserId, loginUser.getUserId()));
        letterMapper.delete(wrapper);
    }

    /**
     * 显示所有私信数
     */
    @Override
    public MessageCountResponse showAllLetterCount(LoginUser loginUser) {
        Integer integer = letterMapper.selectCount(
                Wrappers.lambdaQuery(Letter.class)
                        .eq(Letter::getToUserId, loginUser.getUserId())
                        .eq(Letter::getIsRead, false)
        );
        MessageCountResponse messageCountResponse = new MessageCountResponse();
        messageCountResponse.setMessageCount(integer);
        return messageCountResponse;
    }

    /**
     * 清楚私信
     */
    @Override
    public void clearAllLetterMessage(LoginUser loginUser) {
        LambdaUpdateWrapper<Letter> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Letter::getToUserId, loginUser.getUserId())
                .eq(Letter::getIsRead, false)
                .set(Letter::getIsRead, true);
        letterMapper.update(null, wrapper);
    }
}
