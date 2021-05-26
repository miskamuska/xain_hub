package com.somecoder.demo.blog.service.impl;

import com.somecoder.demo.blog.entity.CommentMessage;
import com.somecoder.demo.blog.mapper.CommentMessageMapper;
import com.somecoder.demo.blog.service.ICommentMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liangkeyu
 * @since 2021-02-25
 */
@Service
public class CommentMessageServiceImpl extends ServiceImpl<CommentMessageMapper, CommentMessage> implements ICommentMessageService {

}
