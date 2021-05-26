package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/26 16:21
 * @description：TODO
 */
@Data
public class ShowLetterRecordResponse {
    @ApiModelProperty("我的头像")
    private String myHeadPicUrl;

    @ApiModelProperty("我的主键")
    private String myUserId;

    @ApiModelProperty("好友姓名")
    private String friendName;

    @ApiModelProperty("好友主键")
    private String friendUserId;

    @ApiModelProperty("好友头像")
    private String headPicUrl;

    @ApiModelProperty("消息记录列表")
    private List<RecordResponse> record;
}
