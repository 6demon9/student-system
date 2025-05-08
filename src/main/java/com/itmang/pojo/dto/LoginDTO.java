package com.itmang.pojo.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@ApiModel(description = "登录信息")
@Data
public class LoginDTO {

    @Schema(name = "学号")
    private String number;
    @Schema(name = "密码")
    private String password;

}
