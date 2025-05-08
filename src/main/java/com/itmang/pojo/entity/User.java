package com.itmang.pojo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户实体类")
public class User {

    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("角色id")
    private Long rodeId;
    @ApiModelProperty("学号")
    private String number;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("创建人")
    private Long createBy;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("修改人")
    private Long updateBy;
}