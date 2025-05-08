package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDTO",description = "查询条件信息")
public class UserPageDTO {
    @Schema(name = "number",description = "学号")
    private String number;
    @Schema(name = "name",description = "姓名")
    private String name;
    @Schema(name = "sex",description = "性别")
    private Integer sex;
    @Schema(name = "age",description = "年龄")
    private Integer age;
    @Schema(name = "address",description = "地址")
    private String address;
    @Schema(name = "status",description = "状态")
    private Integer status;
    @Schema(name = "pageNum",description = "当前页")
    private Integer pageNum;
    @Schema(name = "pageSize",description = "每页显示条数")
    private Integer pageSize;
}
