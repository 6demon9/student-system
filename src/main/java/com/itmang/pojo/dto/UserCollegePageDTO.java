package com.itmang.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserCollegeDTO",description = "查询学院中的用户条件信息DTO")
public class UserCollegePageDTO implements Serializable {

    @Schema(name = "collegeId",description = "学院id")
    private Long collegeId;
    @Schema(name = "pageNum",description = "当前页码")
    private int pageNum;
    @Schema(name = "pageSize",description = "每页显示条数")
    private int pageSize;
}
