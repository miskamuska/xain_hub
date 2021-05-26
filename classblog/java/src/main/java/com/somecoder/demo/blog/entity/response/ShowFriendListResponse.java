package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/26 10:15
 * @description：TODO
 */
@Data
public class ShowFriendListResponse {
    @ApiModelProperty("姓名")
    private String friendName;
    @ApiModelProperty("好友用户主键")
    private String friendUserId;
    @ApiModelProperty("最后一条内容")
    private String lastContent;
    @ApiModelProperty("时间")
    private String time;
    @ApiModelProperty("头像")
    private String headPicUrl;
    @ApiModelProperty("未读消息数")
    private Integer count;
    @ApiModelProperty("是否已读")
    private Boolean isRead;
    @ApiModelProperty("接收人")
    private String toUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowFriendListResponse that = (ShowFriendListResponse) o;
        return Objects.equals(friendUserId, that.friendUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendUserId);
    }
}
