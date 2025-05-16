package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CollegeDTO",description = "查询学院条件信息DTO")
public class CollegePageDTO implements Serializable {
    @Schema(name = "name",description = "学院名称")
    private String name;
    @Schema(name = "pageNum",description = "当前页")
    private int pageNum;
    @Schema(name = "pageSize",description = "每页显示条数")
    private int pageSize;
}
