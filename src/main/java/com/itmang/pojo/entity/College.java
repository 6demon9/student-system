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
@Schema(name = "College",description = "学院实体类")
public class College {

    @Schema(name = "id",description = "学院id")
    private Long id;
    @Schema(name = "name",description = "学院名称")
    private String name;
    @Schema(name = "num",description = "学院人数")
    private Integer num;
    @Schema(name = "createTime",description = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "createBy",description = "创建人")
    private Long createBy;
    @Schema(name = "updateTime",description = "修改时间")
    private LocalDateTime updateTime;
    @Schema(name = "updateBy",description = "修改人")
    private Long updateBy;
}
