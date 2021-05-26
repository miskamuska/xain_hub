package com.somecoder.demo.blog.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("修改学生信息实体类")
public class ChangeInformationRequest {

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "个性签名")
    private String personalSignature;

    @ApiModelProperty(value = "出生年")
    private String birthdayYear;

    @ApiModelProperty(value = "出生月")
    private String birthdayMonth;

    @ApiModelProperty(value = "出生日")
    private String birthdayDay;

    @ApiModelProperty(value = "星座")
    private String constellation;

    @ApiModelProperty(value = "所在地")
    private String location;
}
