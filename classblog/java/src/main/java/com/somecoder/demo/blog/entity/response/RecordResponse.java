package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：lishan
 * @date ：Created in 2021/2/26 16:28
 * @description：TODO
 */
@Data
public class RecordResponse {
    @ApiModelProperty("记录主键")
    private String letterId;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("是不是我发送的")
    private Boolean isMe;
    @ApiModelProperty("时间")
    private String time;
}
