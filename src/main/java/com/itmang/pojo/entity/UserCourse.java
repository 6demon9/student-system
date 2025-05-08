package com.itmang.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserCourse",description = "选课实体类")
public class UserCourse {

    @Schema(name = "id",description = "选课id")
    private Long id;
    @Schema(name = "userId",description = "用户id")
    private Long userId;
    @Schema(name = "collegeId",description = "课程id")
    private Long collegeId;
    @Schema(name = "score",description = "课程分数")
    private Integer score;
    @Schema(name = "createTime",description = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "createBy",description = "创建人")
    private Long createBy;
    @Schema(name = "updateTime",description = "修改时间")
    private LocalDateTime updateTime;
    @Schema(name = "updateBy",description = "修改人")
    private Long updateBy;
}