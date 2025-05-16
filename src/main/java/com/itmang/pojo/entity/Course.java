package com.itmang.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Course",description = "课程实体类")
public class Course implements Serializable {

    @Schema(name = "id",description = "课程id")
    private Long id;
    @Schema(name = "code",description = "课程代码")
    private String code;
    @Schema(name = "name",description = "课程名字")
    private String name;
    @Schema(name = "state",description = "课程说明")
    private String state;
    @Schema(name = "createTime",description = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "createBy",description = "创建人")
    private Long createBy;
    @Schema(name = "updateTime",description = "修改时间")
    private LocalDateTime updateTime;
    @Schema(name = "updateBy",description = "修改人")
    private Long updateBy;
}