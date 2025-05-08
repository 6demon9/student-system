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
@Schema(name = "Permission",description = "权限实体类")
public class Permission {

    @Schema(name = "id",description = "权限id")
    private Long id;
    @Schema(name = "permissionName",description = "权限名称")
    private String permissionName;
    @Schema(name = "description",description = "权限描述")
    private String description;
    @Schema(name = "createTime",description = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "createBy",description = "创建人")
    private Long createBy;
    @Schema(name = "updateTime",description = "修改时间")
    private LocalDateTime updateTime;
    @Schema(name = "updateBy",description = "修改人")
    private Long updateBy;
}