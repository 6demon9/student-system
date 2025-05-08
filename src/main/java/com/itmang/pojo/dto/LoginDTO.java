package com.itmang.pojo.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(name = "LoginDTO",description = "登录信息")
@Data
public class LoginDTO {

    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "password",description = "密码")
    private String password;

}
