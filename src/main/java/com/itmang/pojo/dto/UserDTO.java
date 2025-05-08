package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDTO",description = "编辑用户信息")
public class UserDTO {

    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "name",description = "姓名")
    private String name;
    @Schema(name = "sex",description = "性别")
    private Integer sex;
    @Schema(name = "address",description = "地址")
    private String address;
}
