package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDTO",description = "编辑用户信息DTO")
public class UserDTO implements Serializable {

    @Schema(name = "id",description = "用户id")
    private Long id;
    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "password",description = "密码")
    private String password;
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
}
