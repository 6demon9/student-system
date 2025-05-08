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
@ApiModel(description = "课程实体类")
public class Course {

    @ApiModelProperty("课程id")
    private Long id;
    @ApiModelProperty("课程代码")
    private String code;
    @ApiModelProperty("课程名字")
    private String name;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("创建人")
    private Long createBy;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("修改人")
    private Long updateBy;
}