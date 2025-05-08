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
@ApiModel(description = "选课实体类")
public class UserCourse {

    @ApiModelProperty("选课id")
    private Long id;
    @ApiModelProperty("用户id")
    private Long rodeId;
    @ApiModelProperty("课程id")
    private Long collegeId;
    @ApiModelProperty("课程分数")
    private Integer score;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("创建人")
    private Long createBy;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("修改人")
    private Long updateBy;
}