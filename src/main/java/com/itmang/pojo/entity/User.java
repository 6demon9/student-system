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
@Schema(name = "User",description = "用户实体类")
public class User {

    @Schema(name = "id",description = "用户id")
    private Long id;
    @Schema(name = "roleId",description = "角色id")
    private Long roleId;
    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "password",description = "密码")
    private String password;
    @Schema(name = "status",description = "状态")
    private Integer status;
    @Schema(name = "name",description = "姓名")
    private String name;
    @Schema(name = "sex",description = "性别")
    private Integer sex;
    @Schema(name = "age",description = "年龄")
    private Integer age;
    @Schema(name = "phone",description = "电话")
    private String phone;
    @Schema(name = "address",description = "地址")
    private String address;
    @Schema(name = "createTime",description = "创建时间")
    private LocalDateTime createTime;
    @Schema(name = "createBy",description = "创建人")
    private Long createBy;
    @Schema(name = "updateTime",description = "修改时间")
    private LocalDateTime updateTime;
    @Schema(name = "updateBy",description = "修改人")
    private Long updateBy;
}