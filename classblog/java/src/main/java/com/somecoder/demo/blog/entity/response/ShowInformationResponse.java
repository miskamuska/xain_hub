package com.somecoder.demo.blog.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Liangkeyu
 * @since 2021-01-28
 */
@Data
@ApiModel("展示学生信息响应")
public class ShowInformationResponse {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "邮箱")
    private String email;

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
